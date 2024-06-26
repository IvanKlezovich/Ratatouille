package org.example.paymentservice.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long number;
    private String name;
    private byte expirationMonth;
    private byte expirationYear;

}
