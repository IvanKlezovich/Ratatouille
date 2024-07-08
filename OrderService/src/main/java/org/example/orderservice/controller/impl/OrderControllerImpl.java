package org.example.orderservice.controller.impl;

import org.example.orderservice.controller.OrderController;
import org.example.orderservice.dto.request.DishesRequestDto;
import org.example.orderservice.dto.response.OrderResponseDto;
import org.example.orderservice.service.impl.DishesServiceImpl;
import org.example.orderservice.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderControllerImpl implements OrderController {

    private final OrderServiceImpl orderService;
    private final DishesServiceImpl dishService;

    @Autowired
    public OrderControllerImpl(OrderServiceImpl orderService, DishesServiceImpl dishService) {
        this.orderService = orderService;
        this.dishService = dishService;
    }

    @PostMapping("/add_dishes/")
    public ResponseEntity<Void> addDishToOrder(@PathVariable int id, @RequestBody DishesRequestDto dish) {
        orderService.addDishToOrder(id, dish);
        dishService.addDishes(dish);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete_dishes/{dishId}/{orderId}")
    public ResponseEntity<Void> deleteDishFromOrders(@PathVariable int dishId, @PathVariable int orderId) {
        orderService.deleteDishFromOrders(dishId, orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete_order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/buy")
    public ResponseEntity<Void> buyOrder(@RequestBody OrderResponseDto orderResponseDto) {
        return ResponseEntity.ok().build();
    }

}
