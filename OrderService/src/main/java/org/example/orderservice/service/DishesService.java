package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.DishesDto;
import org.example.orderservice.repository.DishesRepository;
import org.example.orderservice.util.DishesMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishesService {

    private final DishesRepository dishesRepository;

    public void addDishes(DishesDto dishesDto) {
        dishesRepository.save(DishesMapper.INSTANCE.fromDto(dishesDto));
    }
}

