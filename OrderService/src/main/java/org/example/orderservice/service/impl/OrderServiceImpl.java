package org.example.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.request.DishesRequestDto;
import org.example.orderservice.handler.exceptions.ResourceNotFoundException;
import org.example.orderservice.model.Dishes;
import org.example.orderservice.model.Order;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.service.OrderService;
import org.example.orderservice.util.DishesMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public void deleteOrder(int orderId){
        orderRepository.deleteById(orderId);
    }

    public void deleteDishFromOrders(int dishId, int orderId) {
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

    public void addDishToOrder(int idOrder, DishesRequestDto dishDto){
        Order order = orderRepository.findById(idOrder).orElseThrow(() -> new RuntimeException("Заказ не найден"));
        Dishes dish = DishesMapper.INSTANCE.fromDto(dishDto);
        order.getDishes().add(dish);
        double costToAdd = dish.getPrice() * dish.getCount();
        updateOrderCost(order, costToAdd);
        orderRepository.save(order);
    }

    private void updateOrderCost(Order order, double costChange){
        double updatedCost = order.getOrderCost() + costChange;
        order.setOrderCost(updatedCost);
    }

}
