package org.example.paymentservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class CardController {

    @GetMapping("/pay")
    public ResponseEntity<String> pay() {
        if(true){
            return ResponseEntity.status(200).body("Payment Successful");
        }
        return ResponseEntity.status(404).body("Payment Not Found");
    }
    //TODO CRUD for card to save it, when we have profile
}
