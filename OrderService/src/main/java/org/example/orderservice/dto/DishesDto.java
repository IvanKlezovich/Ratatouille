package org.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishesDto {
    private int id;
    private String name;
    private int count;
    private double price;
    private OrderDto orderDto;
}
