package org.example.restarauntservice.service;

import org.example.restarauntservice.dto.DishesDto;

import java.util.List;

public interface DishesService {
    List<DishesDto> readDishes();
    void deleteCountDishesById(int id, int count);
}
