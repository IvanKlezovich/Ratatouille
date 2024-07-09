package com.aston.homework.deliveryservice.controller;

import com.aston.homework.deliveryservice.dto.response.DeliveryResponseDto;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("/api/delivery")
public interface DeliveryController {

    @PostMapping("/{orderId}")
    ResponseEntity<Boolean> deliver(@NotBlank @PathVariable String orderId);

}
