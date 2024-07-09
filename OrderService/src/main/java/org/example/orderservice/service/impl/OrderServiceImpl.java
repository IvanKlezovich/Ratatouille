package org.example.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.request.DishesRequestDto;
import org.example.orderservice.dto.request.OrderCompensationRequestDto;
import org.example.orderservice.dto.request.OrderDeliveryRequestDto;
import org.example.orderservice.dto.request.OrderRequestDto;
import org.example.orderservice.dto.response.*;
import org.example.orderservice.handler.exceptions.ResourceNotFoundException;
import org.example.orderservice.model.Dishes;
import org.example.orderservice.model.Order;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.service.OrderService;
import org.example.orderservice.util.DishesMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, Object> orderKafkaTemplate;

    private final KafkaTemplate<String, Object> paymentKafkaTemplate;

    private final KafkaTemplate<String, Object> deliveryKafkaTemplate;

    private final ConcurrentHashMap<Long, OrderSagaData> sagaDataMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Long, CompletableFuture<OrderSagaData>> sagaFutures = new ConcurrentHashMap<>();


    public ResponseEntity<OrderSagaData> startOrderSaga(@RequestBody OrderRequestDto requestDto) {
        long orderId = requestDto.getId();
        OrderSagaData sagaData = new OrderSagaData();
        sagaData.setOrderId(orderId);
        sagaData.setStatus("ORDER_STARTED");

        CompletableFuture<OrderSagaData> sagaFuture = new CompletableFuture<>();
        sagaDataMap.put(orderId, sagaData);
        sagaFutures.put(orderId, sagaFuture);

        // Отправляем запрос в PaymentService
        paymentKafkaTemplate.send("payment-request-topic", requestDto);

        try {
            // Ожидаем завершения саги
            OrderSagaData completedSagaData = sagaFuture.get();
            return ResponseEntity.ok(completedSagaData);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @KafkaListener(topics = "payment-response-topic", groupId = "order-group")
    public void handlePaymentResponseEvent(PayResponseDto payResponse) {
        long orderId = payResponse.getId();
        OrderSagaData sagaData = sagaDataMap.get(orderId);
        CompletableFuture<OrderSagaData> sagaFuture = sagaFutures.get(orderId);

        if ("PAYMENT_SUCCESS".equals(payResponse.getStatus())) {
            sagaData.setPaymentStatus("PAYMENT_SUCCESS");
            OrderDeliveryRequestDto deliveryRequest = new OrderDeliveryRequestDto();
            deliveryRequest.setOrderId(orderId);
            deliveryKafkaTemplate.send("delivery-request-topic", deliveryRequest);
        } else {
            sagaData.setPaymentStatus("PAYMENT_FAILED");
            sagaData.setStatus("ORDER_FAILED");
            orderKafkaTemplate.send("order-compensation-topic", new OrderCompensationRequestDto(orderId));
            sagaFuture.complete(sagaData);
        }
    }

    @KafkaListener(topics = "delivery-response-topic", groupId = "order-group")
    public void handleDeliveryResponseEvent(OrderDeliveryResponseDto deliveryResponse) {
        long orderId = deliveryResponse.getId();
        OrderSagaData sagaData = sagaDataMap.get(orderId);
        CompletableFuture<OrderSagaData> sagaFuture = sagaFutures.get(orderId);

        if ("DELIVERY_SUCCESS".equals(deliveryResponse.getStatus())) {
            sagaData.setDeliveryStatus("DELIVERY_SUCCESS");
            sagaData.setStatus("ORDER_COMPLETED");
        } else {
            sagaData.setDeliveryStatus("DELIVERY_FAILED");
            sagaData.setStatus("ORDER_FAILED");
            orderKafkaTemplate.send("order-compensation-topic", new OrderCompensationRequestDto(orderId));
        }
        sagaFuture.complete(sagaData);
    }

    @KafkaListener(topics = "order-compensation-topic", groupId = "order-group")
    public void handleOrderCompensationEvent(OrderCompensationRequestDto compensationRequestDto) {
        long orderId = compensationRequestDto.getId();
        OrderSagaData sagaData = sagaDataMap.get(orderId);
        CompletableFuture<OrderSagaData> sagaFuture = sagaFutures.get(orderId);

        sagaData.setStatus("ORDER_COMPENSATED");
        deleteOrder(orderId);

        sagaFuture.complete(sagaData);
    }

    public void deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }


    public void deleteDishFromOrders(int dishId, long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Заказ с  id: " + orderId + " не найден"));
        Dishes dishToRemove = order.getDishes().stream()
                .filter(dish -> dish.getId() == dishId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Блюдо с id: " + dishId + " не найдено"));
        order.setOrderCost(order.getOrderCost() - dishToRemove.getPrice());
        order.getDishes().remove(dishToRemove);
        orderRepository.save(order);
    }

    public void addDishToOrder(long idOrder, DishesRequestDto dishDto) {
        Order order = orderRepository.findById(idOrder).orElseThrow(() -> new RuntimeException("Заказ не найден"));
        Dishes dish = DishesMapper.INSTANCE.fromDto(dishDto);
        order.getDishes().add(dish);
        double costToAdd = dish.getPrice() * dish.getCount();
        updateOrderCost(order, costToAdd);
        orderRepository.save(order);
    }

    private void updateOrderCost(Order order, double costChange) {
        double updatedCost = order.getOrderCost() + costChange;
        order.setOrderCost(updatedCost);
    }

}
