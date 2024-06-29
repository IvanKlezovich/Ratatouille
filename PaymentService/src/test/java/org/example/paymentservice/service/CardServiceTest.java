package org.example.paymentservice.service;

import jakarta.transaction.Transactional;
import org.example.paymentservice.dtos.CardDTO;
import org.example.paymentservice.entities.Card;
import org.example.paymentservice.entities.Pay;
import org.example.paymentservice.repositories.CardRepository;
import org.example.paymentservice.repositories.PayRepository;
import org.example.paymentservice.util.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The {@code CardServiceTest} class is a test class for the {@link CardService} class.
 * It uses Spring Boot's testing utilities to test the card service functionality.
 */
@SpringBootTest
class CardServiceTest {

    /**
     * The {@code CardService} instance used to perform card service operations.
     */
    @Autowired
    private CardService cardService;

    /**
     * The {@code CardRepository} instance, mocked for the purpose of this test.
     */
    @MockBean
    private CardRepository cardRepository;

    /**
     * The {@code BankService} instance, mocked for the purpose of this test.
     */
    @MockBean
    private BankService bankService;

    /**
     * The {@code Mapper} instance, mocked for the purpose of this test.
     */
    @MockBean
    private Mapper mapper;

    /**
     * The {@code PayRepository} instance, mocked for the purpose of this test.
     */
    @MockBean
    private PayRepository payRepository;

    /**
     * Tests the successful payment scenario.
     * Asserts that the payment is successful and the pay repository save method is called.
     */
    @Test
    @Transactional
    void testPaySuccess() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNumber("1234567890123456");
        cardDTO.setName("John Doe");
        cardDTO.setData("12/23");

        Card card = new Card();
        card.setNumber(1234567890123456L);
        card.setName("John Doe");
        card.setExpirationMonth((byte) 12);
        card.setExpirationYear((byte) 23);

        when(cardRepository.findByNumber(1234567890123456L)).thenReturn(card);
        when(bankService.minusBalance(1234567890123456L, 100.0)).thenReturn(true);
        when(bankService.addBalance(100.0)).thenReturn(true);

        boolean result = cardService.pay(cardDTO, 100.0, new Date());
        assertTrue(result);
        verify(payRepository, times(1)).save(any(Pay.class));
    }

    /**
     * Tests the payment failure scenario.
     * Asserts that the payment fails and the pay repository save method is not called.
     */
    @Test
    @Transactional
    void testPayFailure() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNumber("1234567890123456");
        cardDTO.setName("John Doe");
        cardDTO.setData("12/23");

        Card card = new Card();
        card.setNumber(1234567890123456L);
        card.setName("John Doe");
        card.setExpirationMonth((byte) 12);
        card.setExpirationYear((byte) 23);

        when(cardRepository.findByNumber(1234567890123456L)).thenReturn(card);
        when(bankService.minusBalance(1234567890123456L, 100.0)).thenReturn(false);

        boolean result = cardService.pay(cardDTO, 100.0, new Date());
        assertFalse(result);
        verify(payRepository, never()).save(any(Pay.class));
    }

    /**
     * Tests the retrieval of all cards.
     * Asserts that the list of card DTOs is correctly returned.
     */
    @Test
    @Transactional
    void testAll() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNumber("1234567890123456");
        cardDTO.setName("John Doe");
        cardDTO.setData("12/23");

        Card card = new Card();
        card.setNumber(1234567890123456L);
        card.setName("John Doe");
        card.setExpirationMonth((byte) 12);
        card.setExpirationYear((byte) 23);

        List<Card> cards = new ArrayList<>();
        cards.add(card);

        when(cardRepository.findAll()).thenReturn(cards);
        when(mapper.mapToCardDTO(card)).thenReturn(cardDTO);

        List<CardDTO> result = cardService.all();
        assertEquals(1, result.size());
        assertEquals(cardDTO, result.get(0));
    }

    /**
     * Tests the addition of a new card.
     * Asserts that the card repository save method and bank service add bank account method are called.
     */
    @Test
    void testAddCard() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNumber("1234567890123456");
        cardDTO.setName("John Doe");
        cardDTO.setData("12/23");

        Card card = new Card();
        card.setNumber(1234567890123456L);
        card.setName("John Doe");
        card.setExpirationMonth((byte) 12);
        card.setExpirationYear((byte) 23);

        when(mapper.mapToCard(cardDTO)).thenReturn(card);

        cardService.addCard(cardDTO);
        verify(cardRepository, times(1)).save(card);
        verify(bankService, times(1)).addBankAccount(1234567890123456L);
    }
}
