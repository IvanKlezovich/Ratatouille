//package org.example.paymentservice.controller.impl;
//
//import org.example.paymentservice.data.constant.PayStatus;
//import org.example.paymentservice.dto.request.PayRequestDto;
//import org.example.paymentservice.dto.response.PayResponseDto;
//import org.example.paymentservice.service.PayService;
//import org.junit.jupiter.api.Test;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.example.paymentservice.data.constant.PayStatus.PENDING;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(PaymentControllerImpl.class)
//class PaymentControllerImplTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PayService payService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testPaySuccess() throws Exception {
//        PayRequestDto requestDto = new PayRequestDto();
//        requestDto.setOrderId(1L);
//        requestDto.setOrderCost(100L);
//        requestDto.setStatus(PENDING);
//
//        PayResponseDto responseDto = new PayResponseDto(1L);
//
//        when(payService.pay(any(PayRequestDto.class))).thenReturn(responseDto);
//
//        mockMvc.perform(post("/api/payment/pay")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.payId").value(1L));
//    }
//
//    @Test
//    void testPayRollbackSuccess() throws Exception {
//        PayRequestDto requestDto = new PayRequestDto();
//        requestDto.setOrderId(1L);
//        requestDto.setOrderCost(100L);
//        requestDto.setStatus(PayStatus.valueOf("PENDING"));
//
//        PayResponseDto responseDto = new PayResponseDto(1L);
//
//        mockMvc.perform(post("/api/payment/payRollback")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.payId").value(1L));
//    }
//}