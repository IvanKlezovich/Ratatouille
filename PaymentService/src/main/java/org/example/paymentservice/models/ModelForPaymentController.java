package org.example.paymentservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.paymentservice.dtos.CardDTO;

@Data
public class ModelForPaymentController {
    @JsonProperty("CardDTO")
    private final CardDTO cardDTO;
    private final Double sum;
}
