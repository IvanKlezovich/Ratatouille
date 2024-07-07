package com.aston.homework.deliveryservice.exception;

import com.aston.homework.deliveryservice.exception.dto.DeliveryNotAvailabilityResponseDto;
import lombok.Getter;

@Getter
public class DeliveryException extends RuntimeException {
    private final DeliveryNotAvailabilityResponseDto response;

    public DeliveryException(String message, DeliveryNotAvailabilityResponseDto response) {
        super(message);
        this.response = response;
    }

}