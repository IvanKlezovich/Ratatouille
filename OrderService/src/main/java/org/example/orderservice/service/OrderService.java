package org.example.orderservice.service;

import org.example.orderservice.dto.request.DishesRequestDto;
import org.example.orderservice.dto.request.OrderRequestDto;
import org.example.orderservice.dto.response.OrderResponseDto;
import org.example.orderservice.dto.response.OrderSagaData;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    void deleteOrder(long orderId);
    void deleteDishFromOrders(int dishId, long orderId);
    void addDishToOrder(long idOrder, DishesRequestDto dishDto);
    ResponseEntity<OrderSagaData> startOrderSaga(OrderRequestDto requestDto);
}
