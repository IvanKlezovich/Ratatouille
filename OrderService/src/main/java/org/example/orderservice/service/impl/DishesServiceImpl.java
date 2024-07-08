package org.example.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.request.DishesRequestDto;
import org.example.orderservice.repository.DishesRepository;
import org.example.orderservice.service.DishesService;
import org.example.orderservice.util.DishesMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishesServiceImpl implements DishesService<DishesRequestDto> {
    private final DishesRepository dishesRepository;

    public void addDishes(DishesRequestDto dishesDto) {
        dishesRepository.save(DishesMapper.INSTANCE.fromDto(dishesDto));
    }
}

