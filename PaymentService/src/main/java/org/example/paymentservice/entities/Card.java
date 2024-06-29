package org.example.paymentservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

/**
 * The {@code Card} class is a JPA entity representing a credit or debit card. It includes fields for an ID,
 * card number, cardholder name, expiration date, and a relationship to a set of {@link Pay} entities.
 */
@Data
@Entity
public class Card {

    /**
     * The unique identifier for the card.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The card number.
     */
    private Long number;

    /**
     * The name of the cardholder.
     */
    private String name;

    /**
     * The expiration month of the card.
     */
    private byte expirationMonth;

    /**
     * The expiration year of the card.
     */
    private byte expirationYear;

    /**
     * The set of payments associated with this card. This is a one-to-many relationship, indicating that a
     * single card can be associated with multiple payments.
     */
    @OneToMany
    private Set<Pay> setPay;
}
