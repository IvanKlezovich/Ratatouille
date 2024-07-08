package org.example.restarauntservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.restarauntservice.dto.DishesDto;
import org.example.restarauntservice.model.Dishes;
import org.example.restarauntservice.repository.DishesRepository;
import org.example.restarauntservice.service.DishesService;
import org.example.restarauntservice.util.DishesMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishesServiceImpl implements DishesService {

    private final DishesRepository dishesRepository;

    public List<DishesDto> readDishes() {
        List<Dishes> courses = dishesRepository.findAll();
        return courses.stream().map(DishesMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteCountDishesById(int id, int count) {
        Dishes dish = dishesRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Данного блюда нет в наличии"));
        int currentCount = dish.getCount();
        int newCount = currentCount - count;

        if (newCount < 0) {
            throw new IllegalArgumentException("Ингридиенты для данного блюда закончились");
        }

        dish.setCount(newCount);
        dishesRepository.save(dish);
    }

}

