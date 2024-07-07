//package org.example.paymentservice.service.impl;
//
//import org.example.paymentservice.data.entity.Pay;
//import org.example.paymentservice.dto.request.PayRequestDto;
//import org.example.paymentservice.dto.response.PayResponseDto;
//import org.example.paymentservice.repository.PayRepository;
//
//import org.example.paymentservice.service.
//import org.example.paymentservice.util.PayMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.example.paymentservice.data.constant.PayStatus.PENDING;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class PayServiceImplTest {
//
//    @Mock
//    private PayRepository payRepository;
//
//    @Mock
//    private PayMapper payMapper;
//
//    @InjectMocks
//    private PaymentServiceImpl paymentService;
//
//    @Captor
//    private ArgumentCaptor<Pay> payCaptor;
//
//    private PayRequestDto requestDto;
//
//    @BeforeEach
//    void setUp() {
//        requestDto = new PayRequestDto();
//        requestDto.setOrderId(1L);
//        requestDto.setOrderCost(100L);
//        requestDto.setStatus(PENDING);
//    }
//
//    @Test
//    void testPaySuccess() {
//        Pay pay = new Pay();
//        pay.setOrderId(1L);
//        pay.setCost(100L);
//        pay.setStatus(PENDING);
//
//        Pay savedPay = new Pay();
//        savedPay.setId(1L);
//        savedPay.setOrderId(1L);
//        savedPay.setCost(100L);
//        savedPay.setStatus(PENDING);
//
//        when(payMapper.toPay(requestDto)).thenReturn(pay);
//        when(payRepository.save(pay)).thenReturn(savedPay);
//        when(payRepository.findByOrderId(1L)).thenReturn(savedPay);
//        when(payMapper.toPayRequestDto(savedPay)).thenReturn(new PayResponseDto(1L));
//
//        PayResponseDto response = paymentService.pay(requestDto);
//
//        assertEquals(1L, response.getPayId());
//
//        verify(payRepository, times(1)).save(payCaptor.capture());
//        Pay capturedPay = payCaptor.getValue();
//        assertEquals(1L, capturedPay.getOrderId());
//        assertEquals(100L, capturedPay.getCost());
//        assertEquals("PENDING", capturedPay.getStatus());
//    }
//
//    @Test
//    void testPayRollbackSuccess() {
//        long orderId = 1L;
//
//        PayResponseDto response = paymentService.payRollback(orderId);
//
//        assertEquals(1L, response.getPayId());
//
//        verify(payRepository, times(1)).deleteByOrderId(orderId);
//    }
//
//    @Test
//    void testPayRollbackFailure() {
//        long orderId = -1L;
//
//        PayResponseDto response = paymentService.payRollback(orderId);
//
//        assertEquals(-1L, response.getPayId());
//
//        verify(payRepository, never()).deleteByOrderId(anyLong());
//    }
//}