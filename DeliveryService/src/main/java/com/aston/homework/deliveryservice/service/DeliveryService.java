package com.aston.homework.deliveryservice.service;

import com.aston.homework.deliveryservice.dto.request.DeliveryRequestDto;
import com.aston.homework.deliveryservice.dto.response.DeliveryResponseDto;

public interface DeliveryService {

    boolean deliverOrder(DeliveryRequestDto requestDto);

}
