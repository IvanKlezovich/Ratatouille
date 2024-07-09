package org.example.orderservice.controller;

import org.example.orderservice.dto.request.DishesRequestDto;
import org.example.orderservice.dto.request.OrderRequestDto;
import org.example.orderservice.dto.response.OrderResponseDto;
import org.example.orderservice.dto.response.OrderSagaData;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/api/order")
public interface OrderController {
    @PostMapping("/add_dishes/")
    ResponseEntity<Void> addDishToOrder(@PathVariable int id, @RequestBody DishesRequestDto dish);

    @PostMapping("/delete_dishes/{dishId}/{orderId}")
    public ResponseEntity<Void> deleteDishFromOrders(@PathVariable int dishId, @PathVariable int orderId);

    @PostMapping("/delete_order/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable int id);

    @PostMapping("/buy")
    ResponseEntity<OrderSagaData> buyOrder(@RequestBody OrderRequestDto orderRequestDto);

}
