package org.example.paymentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.paymentservice.data.constant.PayStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayRequestDto {
    private long orderId;
    private long orderCost;
    private PayStatus status;
}
