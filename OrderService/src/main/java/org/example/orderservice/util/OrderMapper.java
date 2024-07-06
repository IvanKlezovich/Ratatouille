package org.example.orderservice.util;

import org.example.orderservice.dto.DishesDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.model.Dishes;
import org.example.orderservice.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "dishes", target = "dishesDto")
    OrderDto toDto(Order order);

    @Mapping(source = "dishesDto", target = "dishes")
    Order fromDto(OrderDto orderDto);
}
