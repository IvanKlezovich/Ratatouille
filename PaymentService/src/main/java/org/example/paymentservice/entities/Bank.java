package org.example.paymentservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numberCard;
    private Double balance;
}
