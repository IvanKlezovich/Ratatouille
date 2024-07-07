package org.example.paymentservice.controller.impl;

import lombok.RequiredArgsConstructor;
import org.example.paymentservice.controller.PaymentController;
import org.example.paymentservice.dto.request.PayRequestDto;
import org.example.paymentservice.dto.response.PayResponseDto;
import org.example.paymentservice.service.PayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment/")
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final PayService payService;

    public ResponseEntity<PayResponseDto> pay(@RequestBody PayRequestDto payRequestDto){
        return ResponseEntity.ok(payService.pay(payRequestDto));
    }

    public ResponseEntity<PayResponseDto> payRollback(@RequestBody PayRequestDto payResponseDto){
        return ResponseEntity.ok(new PayResponseDto(1L));
    }
}