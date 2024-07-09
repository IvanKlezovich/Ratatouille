package org.example.paymentservice.util;

import org.example.paymentservice.data.entity.Pay;
import org.example.paymentservice.dto.request.PayRequestDto;
import org.example.paymentservice.dto.response.PayResponseDto;
import org.springframework.stereotype.Component;

@Component
public final class PayMapper {

    public Pay toPay(PayRequestDto payRequestDto) {
        Pay pay = new Pay();
        pay.setStatus(payRequestDto.getStatus());
        pay.setCost(payRequestDto.getOrderCost());
        pay.setOrderId(payRequestDto.getOrderId());
        return pay;
    }

    public PayResponseDto toPayRequestDto(Pay pay) {
        return new PayResponseDto(pay.getId(), pay.getStatus());
    }
}
