package org.example.orderservice.controller.impl;

import org.example.orderservice.controller.OrderController;
import org.example.orderservice.dto.request.DishesRequestDto;
import org.example.orderservice.dto.request.OrderRequestDto;
import org.example.orderservice.dto.response.OrderSagaData;
import org.example.orderservice.service.impl.DishesServiceImpl;
import org.example.orderservice.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderControllerImpl implements OrderController {

    private final OrderServiceImpl orderService;
    private final DishesServiceImpl dishService;

    @Autowired
    public OrderControllerImpl(OrderServiceImpl orderService, DishesServiceImpl dishService) {
        this.orderService = orderService;
        this.dishService = dishService;
    }

    public ResponseEntity<Void> addDishToOrder(int id, DishesRequestDto dish) {
        orderService.addDishToOrder(id, dish);
        dishService.addDishes(dish);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteDishFromOrders(int dishId, int orderId) {
        orderService.deleteDishFromOrders(dishId, orderId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteOrder(int id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<OrderSagaData> buyOrder(OrderRequestDto orderRequestDto) {
        return orderService.startOrderSaga(orderRequestDto);
    }

}
