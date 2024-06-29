package org.example.paymentservice.util;

import org.example.paymentservice.dtos.CardDTO;
import org.example.paymentservice.entities.Card;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public Card mapToCard(CardDTO cardDTO) {
        String date;
        Card card = new Card();

        date = cardDTO.getData();
        card.setExpirationMonth(Byte.parseByte(date.substring(0, 2)));
        card.setExpirationYear(Byte.parseByte(date.substring(3, 5)));
        card.setNumber(Long.parseLong(cardDTO.getNumber()));
        card.setName(cardDTO.getName());

        return card;
    }

    public CardDTO mapToCardDTO(Card card) {
        CardDTO cardDTO = new CardDTO();

        cardDTO.setData(card.getExpirationMonth() + "/" + card.getExpirationYear());
        cardDTO.setNumber(card.getNumber().toString());
        cardDTO.setName(card.getName());

        return cardDTO;
    }
}
