package com.aston.homework.deliveryservice.exception.handler;

import com.aston.homework.deliveryservice.exception.DeliveryException;
import com.aston.homework.deliveryservice.exception.dto.DeliveryNotAvailabilityResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(DeliveryException.class)
    public ResponseEntity<DeliveryNotAvailabilityResponseDto> handleDeliveryException(DeliveryException ex) {
        return new ResponseEntity<>(ex.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
