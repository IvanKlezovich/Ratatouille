package org.example.paymentservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * The {@code Pay} class is a JPA entity representing a payment record. It includes fields for an ID, a date,
 * and a relationship to a {@link Card} entity.
 */
@Data
@Entity
public class Pay {

    /**
     * The unique identifier for the payment record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The date when the payment was made.
     */
    private Date date;

    /**
     * The card associated with the payment. This is a many-to-one relationship, indicating that multiple
     * payments can be associated with a single card.
     */
    @ManyToOne
    private Card card;
}
