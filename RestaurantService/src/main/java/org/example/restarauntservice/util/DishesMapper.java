package org.example.restarauntservice.util;

import org.example.restarauntservice.dto.DishesDto;
import org.example.restarauntservice.model.Dishes;
import org.mapstruct.Mapper;

@Mapper
public class DishesMapper {

    public static DishesDto toDto(Dishes dishes) {
        return new DishesDto(dishes.getId(), dishes.getName(), dishes.getCount(), dishes.getPrice());
    }

    public static Dishes fromDto(DishesDto dishesDto) {
        return new Dishes(dishesDto.getId(), dishesDto.getName(), dishesDto.getCount(), dishesDto.getPrice());
    }
}
