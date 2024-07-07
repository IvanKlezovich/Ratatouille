package com.aston.homework.deliveryservice.utils;

import com.aston.homework.deliveryservice.data.entity.Delivery;
import com.aston.homework.deliveryservice.dto.request.DeliveryRequestDto;
import com.aston.homework.deliveryservice.dto.response.DeliveryResponseDto;

public final class DeliveryMapper {

    private DeliveryMapper(){}

    public static Delivery toEntity(DeliveryRequestDto dto) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(dto.getOrderId());
        return delivery;
    }

    public static DeliveryResponseDto toDto(Delivery delivery) {
        DeliveryResponseDto responseDto = new DeliveryResponseDto();
        responseDto.setOrderId(delivery.getOrderId());
        responseDto.setStatus(delivery.getDeliveryStatus());
        responseDto.setMessage(delivery.getMessage());
        return responseDto;
    }
}
