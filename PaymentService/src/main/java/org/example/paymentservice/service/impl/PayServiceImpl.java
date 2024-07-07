package org.example.paymentservice.service.impl;

import org.example.paymentservice.data.entity.Pay;
import org.example.paymentservice.dto.request.PayRequestDto;
import org.example.paymentservice.dto.response.PayResponseDto;
import org.example.paymentservice.repository.PayRepository;
import org.example.paymentservice.service.PayService;
import org.example.paymentservice.util.PayMapper;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;
    private final PayMapper payMapper;

    public PayServiceImpl(PayRepository payRepository,
                          PayMapper payMapper) {
        this.payRepository = payRepository;
        this.payMapper = payMapper;
    }

    public PayResponseDto pay(PayRequestDto payRequestDto) {
        if(payRequestDto.getOrderId() > -1 && payRequestDto.getOrderCost() > 0) {
            Pay pay = payMapper.toPay(payRequestDto);
            payRepository.save(pay);
            return payMapper.toPayRequestDto(payRepository.findByOrderId(pay.getOrderId()));
        }
        return new PayResponseDto(-1L);
    }

    public PayResponseDto payRollback(long orderId) {
        if(orderId > -1) {
            payRepository.deleteByOrderId(orderId);
            return new PayResponseDto(orderId);
        }
        return new PayResponseDto(-1L);
    }
}
