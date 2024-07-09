package org.example.paymentservice.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.paymentservice.data.constant.PayStatus;

@Entity
@Data
@Table(name = "pay")
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "orderId")
    private long orderId;

    @Column(name = "cost")
    private long cost;

    @Column(name = "status")
    private String status;
}
