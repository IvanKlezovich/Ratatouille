package org.example.paymentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.paymentservice.data.entity.Pay;
import org.example.paymentservice.dto.request.OrderCompensationRequestDto;
import org.example.paymentservice.dto.request.PayRequestDto;
import org.example.paymentservice.dto.response.PayResponseDto;
import org.example.paymentservice.repository.PayRepository;
import org.example.paymentservice.service.PayService;
import org.example.paymentservice.util.PayMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;

    private final PayMapper payMapper;

    private final KafkaTemplate<String, Object> paymentKafkaTemplate;

    @KafkaListener(topics = "payment-request-topic", groupId = "payment-group")
    public void handlePaymentRequest(PayRequestDto payRequest) {
        PayResponseDto payResponse = new PayResponseDto();
        payResponse.setOrderId(payRequest.getOrderId());
        boolean paymentSuccess = pay(payRequest);

        if (paymentSuccess) {
            payResponse.setStatus("PAYMENT_SUCCESS");
        } else {
            payResponse.setStatus("PAYMENT_FAILED");
        }
        paymentKafkaTemplate.send("payment-response-topic", payResponse);
    }

    @KafkaListener(topics = "order-compensation-topic", groupId = "payment-group")
    public void handleOrderCompensationEvent(OrderCompensationRequestDto compensationRequestDto) {
        long orderId = compensationRequestDto.getId();
        payRollback(orderId);
    }

    public boolean pay(PayRequestDto payRequestDto) {
        if(payRequestDto.getOrderId() > -1 && payRequestDto.getOrderCost() > 0) {
            Pay pay = payMapper.toPay(payRequestDto);
            payRepository.save(pay);
            return true;
        }
        return false;
    }

    public void payRollback(long orderId) {
            payRepository.deleteByOrderId(orderId);
    }
}
