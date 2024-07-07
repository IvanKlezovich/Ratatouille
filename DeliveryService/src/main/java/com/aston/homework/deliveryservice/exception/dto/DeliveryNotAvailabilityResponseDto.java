package com.aston.homework.deliveryservice.exception.dto;

import com.aston.homework.deliveryservice.data.constant.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryNotAvailabilityResponseDto {

    private String orderId;

    private String message;

    private DeliveryStatus status;
}
