package org.example.paymentservice.models;

import lombok.Data;

/**
 * The {@code ModelForServiceController} class is a data transfer object (DTO) used to encapsulate data
 * for operations related to service controllers. It includes fields for a number and a balance.
 */
@Data
public class ModelForServiceController {
    /**
     * The number associated with the service operation.
     */
    private Long number;

    /**
     * The balance associated with the service operation.
     */
    private Double balance;
}
