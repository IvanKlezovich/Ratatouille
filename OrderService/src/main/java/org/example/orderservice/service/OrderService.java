package org.example.orderservice.service;

import org.example.orderservice.dto.request.DishesRequestDto;

public interface OrderService {
    void deleteOrder(int orderId);
    void deleteDishFromOrders(int dishId, int orderId);
    void addDishToOrder(int idOrder, DishesRequestDto dishDto);
}
