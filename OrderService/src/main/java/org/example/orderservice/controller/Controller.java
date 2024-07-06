package org.example.orderservice.controller;

import org.example.orderservice.dto.DishesDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.service.DishesService;
import org.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class Controller {

    private final OrderService orderService;
    private final DishesService dishService;

    @Autowired
    public Controller(OrderService orderService, DishesService dishService) {
        this.orderService = orderService;
        this.dishService = dishService;
    }

    @PostMapping("/add_dishes/")
    public ResponseEntity<Void> addDishToOrder(@PathVariable int id, @RequestBody DishesDto dish) {
        orderService.addDishToOrder(id, dish);
        dishService.addDishes(dish);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete_dishes/{id}")
    public ResponseEntity<Void> deleteDishFromOrders(@PathVariable int id) {
        orderService.deleteDishFromOrders(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete_order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/buy")
    public ResponseEntity<Void> buyOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok().build();
    }

}
