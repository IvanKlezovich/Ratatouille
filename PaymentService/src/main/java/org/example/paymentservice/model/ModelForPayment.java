package org.example.paymentservice.model;

import lombok.Data;

@Data
public class ModelForPayment {
    private long orderId;
    private long orderCost;
}
