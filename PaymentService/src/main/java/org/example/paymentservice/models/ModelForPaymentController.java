package org.example.paymentservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.paymentservice.dtos.CardDTO;

import java.util.Date;

/**
 * The {@code ModelForPaymentController} class is a data transfer object (DTO) used to encapsulate data
 * for payment operations. It includes fields for a card DTO, a sum, and a date.
 */
@Data
public class ModelForPaymentController {
    /**
     * The card DTO containing card-related information.
     */
    @JsonProperty("CardDTO")
    private CardDTO cardDTO;

    /**
     * The sum of the payment.
     */
    private Double sum;

    /**
     * The date of the payment.
     */
    private Date date;
}
