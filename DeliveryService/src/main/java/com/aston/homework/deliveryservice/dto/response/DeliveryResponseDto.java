package com.aston.homework.deliveryservice.dto.response;

import com.aston.homework.deliveryservice.data.constant.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponseDto {

    private String orderId;

    private String message;

    private String status;

}
