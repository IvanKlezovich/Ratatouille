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

    @Column(name = "orderid")
    private long orderId;

    @Column(name = "cost")
    private long cost;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PayStatus status;
}
