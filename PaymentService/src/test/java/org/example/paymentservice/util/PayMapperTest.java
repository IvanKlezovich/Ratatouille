package org.example.paymentservice.util;

import org.example.paymentservice.data.constant.PayStatus;
import org.example.paymentservice.dto.response.PayResponseDto;
import org.example.paymentservice.dto.request.PayRequestDto;
import org.example.paymentservice.data.entity.Pay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PayMapperTest {

    @InjectMocks
    private PayMapper payMapper;

    @Mock
    private PayRequestDto payRequestDto;

    @Mock
    private Pay pay;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToPay() {
        // Arrange
        when(payRequestDto.getStatus()).thenReturn(PayStatus.PENDING);
        when(payRequestDto.getOrderCost()).thenReturn(100L);
        when(payRequestDto.getOrderId()).thenReturn(1L);

        // Act
        Pay result = payMapper.toPay(payRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(PayStatus.PENDING, result.getStatus());
        assertEquals(100L, result.getCost());
        assertEquals(1L, result.getOrderId());
    }

    @Test
    void testToPayRequestDto() {
        // Arrange
        when(pay.getId()).thenReturn(1L);

        // Act
        PayResponseDto result = payMapper.toPayRequestDto(pay);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
    }
}