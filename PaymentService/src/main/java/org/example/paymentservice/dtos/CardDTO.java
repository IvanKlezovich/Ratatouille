package org.example.paymentservice.dtos;

import lombok.Data;

/**
 * The {@code CardDTO} class is a data transfer object (DTO) used to transfer card-related data.
 * It includes fields for the card number, expiration date, and cardholder name.
 */
@Data
public class CardDTO {
    /**
     * The card number.
     */
    private String number;

    /**
     * The expiration date of the card.
     */
    private String data;

    /**
     * The name of the cardholder.
     */
    private String name;
}
