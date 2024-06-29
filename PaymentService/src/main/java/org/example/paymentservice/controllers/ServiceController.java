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

/**
 * The {@code ServiceController} class is a RESTFull controller responsible for handling various service-related operations.
 * It provides endpoints to manage beans, cards, and bank balances.
 */
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
     * Constructor that injects the ApplicationContext and service dependencies.
     *
     * @param context The ApplicationContext to be used by this controller.
     * @param cardService The service responsible for card-related operations.
     * @param bankService The service responsible for bank-related operations.
     */
    public ServiceController(ApplicationContext context, CardService cardService, BankService bankService) {
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
    public List<String> beans() {
        return Arrays.asList(context.getBeanDefinitionNames());
    }

    /**
     * Handles GET requests to retrieve all cards.
     *
     * @return A list of all cards.
     */
    @GetMapping("/allCards")
    public List<CardDTO> allCards() {
        return cardService.all();
    }

    /**
     * Handles POST requests to create a new card.
     *
     * @param cardDto The card data transfer object containing the card details.
     * @return A response entity with a success message.
     */
    @PostMapping("/card")
    public ResponseEntity<String> createCard(@RequestBody CardDTO cardDto) {
        cardService.addCard(cardDto);
        return ResponseEntity.ok("Card created");
    }

    /**
     * Handles POST requests to change the balance of a bank account.
     *
     * @param model The model containing the account number and the new balance.
     * @return A response entity with a success message.
     */
    @PostMapping("/changeBalance")
    public ResponseEntity<String> changeBalance(@RequestBody ModelForServiceController model){
        bankService.changeBalance(model.getNumber(), model.getBalance());
        return ResponseEntity.ok("Balance changed");
    }
}
