package org.example.paymentservice.service;

import org.example.paymentservice.dtos.CardDTO;
import org.example.paymentservice.entities.Card;
import org.example.paymentservice.repositories.CardRepository;
import org.example.paymentservice.util.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    CardRepository cardRepository;
    BankService bankService;
    private final Mapper mapper;

    public CardService(CardRepository cardRepository,
                       BankService bankService,
                       Mapper mapper) {
        this.cardRepository = cardRepository;
        this.bankService = bankService;
        this.mapper = mapper;
    }

    @Transactional
    public boolean pay(CardDTO cardDTO, double sum){
        Card card = cardRepository.findByNumber(Long.parseLong(cardDTO.getNumber()));

        return bankService.minusBalance(card.getNumber(), sum)
                && bankService.addBalance(sum);
    }

    @Transactional(readOnly = true)
    public List<CardDTO> all(){
        List<CardDTO> cardDTOS = new ArrayList<>();
        Iterable<Card> cards = cardRepository.findAll();

        for (Card card : cards) {
            cardDTOS.add(mapper.mapToCardDTO(card));
        }

        return cardDTOS;
    }

    public void addCard(CardDTO cardDTO){
        cardRepository.save(mapper.mapToCard(cardDTO));
        bankService.addBankAccount(mapper.mapToCard(cardDTO).getNumber());
    }
}
