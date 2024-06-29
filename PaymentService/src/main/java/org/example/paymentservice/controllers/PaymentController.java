package org.example.paymentservice.controllers;

import org.example.paymentservice.models.ModelForPaymentController;
import org.example.paymentservice.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code PaymentController} class is a RESTful controller responsible for handling payment-related operations.
 * It provides an endpoint to process payments using a {@link CardService}.
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final CardService cardService;

    /**
     * Constructs a new {@code PaymentController} with the specified {@link CardService}.
     *
     * @param cardService the service used for processing card payments
     */
    public PaymentController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * Processes a payment request.
     *
     * @param model the payment request model containing details such as card information, sum, and date
     * @return a {@link ResponseEntity} indicating the result of the payment operation
     *         - {@code 200 OK} with body "Payment Successful" if the payment is successful
     *         - {@code 404 Not Found} with body "Payment Not Work" if the payment fails
     */
    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody ModelForPaymentController model) {
        if(cardService.pay(model.getCardDTO(), model.getSum(), model.getDate())){
            return ResponseEntity.status(200).body("Payment Successful");
        }
        return ResponseEntity.status(404).body("Payment Not Work");
    }
}
