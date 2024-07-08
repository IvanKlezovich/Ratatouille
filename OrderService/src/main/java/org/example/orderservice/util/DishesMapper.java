package org.example.orderservice.util;

import org.example.orderservice.dto.request.DishesRequestDto;
import org.example.orderservice.model.Dishes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DishesMapper {
    DishesMapper INSTANCE = Mappers.getMapper(DishesMapper.class);

    @Mapping(source = "order", target = "orderDto")
    DishesRequestDto toDto(Dishes dishes);

    @Mapping(source = "orderDto", target = "order")
    Dishes fromDto(DishesRequestDto dishesDto);
}