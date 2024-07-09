package org.example.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.paymentservice.data.constant.PayStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayResponseDto {
    private long orderId;
    private String status;
}
