package org.example.orderservice.util;

import org.example.orderservice.dto.response.OrderResponseDto;
import org.example.orderservice.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderResponseDto orderResponseToDto(Order order);

    Order fromDtoOrderResponse(OrderResponseDto orderResponseDto);
}
