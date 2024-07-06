package org.example.orderservice.util;

import org.example.orderservice.dto.DishesDto;
import org.example.orderservice.model.Dishes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DishesMapper {
    DishesMapper INSTANCE = Mappers.getMapper(DishesMapper.class);

    @Mapping(source = "order", target = "orderDto")
    DishesDto toDto(Dishes dishes);

    @Mapping(source = "orderDto", target = "order")
    Dishes fromDto(DishesDto dishesDto);
}