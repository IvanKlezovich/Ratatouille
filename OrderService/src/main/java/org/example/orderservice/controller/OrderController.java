package org.example.orderservice.controller;

import org.example.orderservice.dto.request.DishesRequestDto;
import org.example.orderservice.dto.response.OrderResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    ResponseEntity<Void> buyOrder(@RequestBody OrderResponseDto orderResponseDto);

}
