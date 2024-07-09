package org.example.paymentservice.controller;

import org.example.paymentservice.dto.request.PayRequestDto;
import org.example.paymentservice.dto.response.PayResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("/api/payment/")
public interface PaymentController {

    @PostMapping("/pay")
    ResponseEntity<Boolean> pay(@RequestBody PayRequestDto payResponseDto);

    @PostMapping("/pay_rollback")
    ResponseEntity<PayResponseDto> payRollback(@RequestBody PayRequestDto payResponseDto);
}
