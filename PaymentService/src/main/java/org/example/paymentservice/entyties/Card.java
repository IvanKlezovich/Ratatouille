package org.example.paymentservice.entyties;

import lombok.Data;


@Data
public class Card {

    private long id;
    private Long number;
    private String name;
    private byte expirationMonth;
    private byte expirationYear;

}
