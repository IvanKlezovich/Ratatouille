package org.example.restarauntservice.controller;

import org.example.restarauntservice.dto.DishesDto;
import org.example.restarauntservice.service.DishesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class DishesController {

    private final DishesService dishesService;

    public DishesController(DishesService dishesService) {
        this.dishesService = dishesService;
    }

    @GetMapping("/getListDishes")
    public List<DishesDto> getListDishes() {
        return dishesService.readDishes();
    }

    @PostMapping("/get_current_dishes")
    public ResponseEntity<String> getCurrentDishes(@RequestBody DishesDto requestDto) {
        dishesService.deleteCountDishesById(requestDto.getId(), requestDto.getCount());
        return ResponseEntity.noContent().build();
    }

}
