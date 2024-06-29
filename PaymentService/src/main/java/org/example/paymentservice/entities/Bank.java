package org.example.paymentservice.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * The {@code Bank} class is a JPA entity representing a bank account. It includes fields for an ID,
 * a card number, and a balance.
 */
@Data
@Entity
public class Bank {

    /**
     * The unique identifier for the bank account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The card number associated with the bank account.
     */
    private Long numberCard;

    /**
     * The balance of the bank account.
     */
    private Double balance;
}
