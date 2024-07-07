package org.example.paymentservice.service;

import org.example.paymentservice.entity.Pay;
import org.example.paymentservice.repository.PayRepository;
import org.springframework.stereotype.Service;

@Service
public class PayService {

    private final PayRepository payRepository;

    public PayService(PayRepository payRepository) {
        this.payRepository = payRepository;
    }

    public boolean pay(long orderId, long cost) {
        if(orderId != -1 && cost != 0) {
            Pay pay = new Pay();
            pay.setOrderId(orderId);
            pay.setCost(cost);
            payRepository.save(pay);
            return true;
        }
        return false;
    }

    public boolean payRollback(long orderId) {
        if(orderId != -1) {
            payRepository.deleteByOrderId(orderId);
            return true;
        }
        return false;
    }
}
