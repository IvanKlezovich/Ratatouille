package org.example.paymentservice.controllers;

import org.example.paymentservice.dtos.CardDTO;
import org.example.paymentservice.models.ModelForPaymentController;
import org.example.paymentservice.service.CardService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final CardService cardService;

    public PaymentController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody ModelForPaymentController model) {
        if(cardService.pay(model.getCardDTO(), model.getSum())){
            return ResponseEntity.status(200).body("Payment Successful");
        }
        return ResponseEntity.status(404).body("Payment Not Found");
    }
}
