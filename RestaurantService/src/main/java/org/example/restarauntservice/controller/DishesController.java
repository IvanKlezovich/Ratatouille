package org.example.restarauntservice.controller;

import org.example.restarauntservice.dto.DishesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Validated
@RequestMapping("/api/restaurant")
public interface DishesController {

    @GetMapping("/getListDishes")
    List<DishesDto> getListDishes();

    @PostMapping("/get_current_dishes")
    ResponseEntity<String> getCurrentDishes(@RequestBody DishesDto requestDto);

}
