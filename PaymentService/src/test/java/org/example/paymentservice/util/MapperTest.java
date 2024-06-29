package org.example.paymentservice.util;

import org.example.paymentservice.dtos.CardDTO;
import org.example.paymentservice.entities.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MapperTest {

    @Autowired
    private Mapper mapper;

    private CardDTO cardDTO;
    private Card card;

    @BeforeEach
    public void setUp() {
        cardDTO = new CardDTO();
        cardDTO.setData("12/23");
        cardDTO.setNumber("1234567890123456");
        cardDTO.setName("John Doe");

        card = new Card();
        card.setExpirationMonth((byte) 12);
        card.setExpirationYear((byte) 23);
        card.setNumber(1234567890123456L);
        card.setName("John Doe");
    }

    @Test
    void testMapToCard() {
        Card mappedCard = mapper.mapToCard(cardDTO);

        assertEquals(card.getExpirationMonth(), mappedCard.getExpirationMonth());
        assertEquals(card.getExpirationYear(), mappedCard.getExpirationYear());
        assertEquals(card.getNumber(), mappedCard.getNumber());
        assertEquals(card.getName(), mappedCard.getName());
    }

    @Test
    void testMapToCardDTO() {
        CardDTO mappedCardDTO = mapper.mapToCardDTO(card);

        assertEquals(cardDTO.getData(), mappedCardDTO.getData());
        assertEquals(cardDTO.getNumber(), mappedCardDTO.getNumber());
        assertEquals(cardDTO.getName(), mappedCardDTO.getName());
    }
}