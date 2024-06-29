package org.example.paymentservice.service;

import org.example.paymentservice.dtos.CardDTO;
import org.example.paymentservice.entities.Card;
import org.example.paymentservice.entities.Pay;
import org.example.paymentservice.repositories.CardRepository;
import org.example.paymentservice.repositories.PayRepository;
import org.example.paymentservice.util.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The {@code CardService} class is a service responsible for handling operations related to cards.
 * It interacts with {@link CardRepository}, {@link BankService}, {@link Mapper}, and {@link PayRepository}
 * to perform CRUD operations and payment processing.
 */
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final BankService bankService;
    private final Mapper mapper;
    private final PayRepository payRepository;

    /**
     * Constructs a new {@code CardService} with the specified dependencies.
     *
     * @param cardRepository The repository used for accessing card data.
     * @param bankService The service responsible for bank-related operations.
     * @param mapper The mapper used for converting between DTOs and entities.
     * @param payRepository The repository used for accessing payment data.
     */
    public CardService(CardRepository cardRepository, BankService bankService, Mapper mapper, PayRepository payRepository) {
        this.cardRepository = cardRepository;
        this.bankService = bankService;
        this.mapper = mapper;
        this.payRepository = payRepository;
    }

    /**
     * Processes a payment using the provided card details, sum, and date.
     *
     * @param cardDTO The card data transfer object containing the card details.
     * @param sum The amount to be paid.
     * @param data The date of the payment.
     * @return {@code true} if the payment was successful, {@code false} otherwise.
     */
    @Transactional
    public boolean pay(CardDTO cardDTO, double sum, Date data) {
        Card card = cardRepository.findByNumber(Long.parseLong(cardDTO.getNumber()));

        if (bankService.minusBalance(card.getNumber(), sum) && bankService.addBalance(sum)) {
            Pay pay = new Pay();
            pay.setCard(card);
            pay.setDate(data);
            payRepository.save(pay);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all cards and maps them to a list of {@link CardDTO}.
     *
     * @return A list of {@link CardDTO} representing all cards.
     */
    @Transactional(readOnly = true)
    public List<CardDTO> all() {
        List<CardDTO> cardDTOS = new ArrayList<>();
        Iterable<Card> cards = cardRepository.findAll();

        for (Card card : cards) {
            cardDTOS.add(mapper.mapToCardDTO(card));
        }

        return cardDTOS;
    }

    /**
     * Adds a new card and creates a corresponding bank account with an initial balance of zero.
     *
     * @param cardDTO The card data transfer object containing the card details.
     */
    public void addCard(CardDTO cardDTO){
        cardRepository.save(mapper.mapToCard(cardDTO));
        bankService.addBankAccount(mapper.mapToCard(cardDTO).getNumber());
    }
}
