package org.example.paymentservice.service;

import org.example.paymentservice.dto.request.PayRequestDto;
import org.example.paymentservice.dto.response.PayResponseDto;

public interface PayService {

    boolean pay(PayRequestDto payRequestDtoDto);

    void payRollback(long orderId);
}
