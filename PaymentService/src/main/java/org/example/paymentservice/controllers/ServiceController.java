package org.example.paymentservice.controllers;

import org.example.paymentservice.dtos.CardDTO;
import org.example.paymentservice.models.ModelForServiceController;
import org.example.paymentservice.service.BankService;
import org.example.paymentservice.service.CardService;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {

    /**
     * The ApplicationContext used to access the beans in the Spring application context.
     */
    private final ApplicationContext context;
    private final CardService cardService;
    private final BankService bankService;

    /**
     * Constructor that injects the ApplicationContext.
     *
     * @param context The ApplicationContext to be used by this controller.
     */
    public ServiceController(ApplicationContext context,
                             CardService cardService,
                             BankService bankService) {
        this.context = context;
        this.cardService = cardService;
        this.bankService = bankService;
    }

    /**
     * Handles GET requests to retrieve the names of all beans in the application context.
     *
     * @return A list of bean names.
     */
    @GetMapping("/beans")
    public @ResponseBody List<String> beans() {
        return Arrays.asList(context.getBeanDefinitionNames());
    }

    @GetMapping("/allCards")
    public @ResponseBody List<CardDTO> allCards() {
        return cardService.all();
    }

    @PostMapping("/card")
    public ResponseEntity<String> createCard(@RequestBody CardDTO cardDto){
        cardService.addCard(cardDto);
        return ResponseEntity.ok("Card created");
    }

    @PostMapping("/changeBalance")
    public ResponseEntity<String> changeBalance(@RequestBody ModelForServiceController model){
        bankService.changeBalance(model.getNumber(), model.getBalance());
        return ResponseEntity.ok("Balance changed");
    }
}
