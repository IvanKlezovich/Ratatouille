package org.example.paymentservice.dtos;

import lombok.Data;

@Data
public class CardDTO {
    private String number;
    private String data;
    private String name;
}
