package org.example.paymentservice.controller;

import org.example.paymentservice.model.ModelForPayment;
import org.example.paymentservice.service.PayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment/")
public class PaymentController {

    private final PayService payService;

    public PaymentController(PayService payService) {
        this.payService = payService;
    }

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody ModelForPayment model){
        if(payService.pay(model.getOrderId(), model.getOrderCost())) return ResponseEntity.ok("Payment successful");
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/pay_rollback")
    public ResponseEntity<String> payRollback(@RequestBody ModelForPayment model){
        return ResponseEntity.ok("Payment rollback successful");
    }
}
