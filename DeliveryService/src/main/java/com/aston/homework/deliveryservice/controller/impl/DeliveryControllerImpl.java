package com.aston.homework.deliveryservice.controller.impl;

import com.aston.homework.deliveryservice.controller.DeliveryController;
import com.aston.homework.deliveryservice.dto.request.DeliveryRequestDto;
import com.aston.homework.deliveryservice.dto.response.DeliveryResponseDto;
import com.aston.homework.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryControllerImpl implements DeliveryController {

    private final DeliveryService deliveryService;

    @Override
    public ResponseEntity<Boolean> deliver(@PathVariable String orderId) {
        DeliveryRequestDto requestDto = new DeliveryRequestDto(orderId);
        boolean response = deliveryService.deliverOrder(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
