package com.aston.homework.deliveryservice.controller.impl;

import com.aston.homework.deliveryservice.controller.DeliveryController;
import com.aston.homework.deliveryservice.data.constant.DeliveryStatus;
import com.aston.homework.deliveryservice.dto.request.DeliveryRequestDto;
import com.aston.homework.deliveryservice.dto.response.DeliveryResponseDto;
import com.aston.homework.deliveryservice.exception.DeliveryException;
import com.aston.homework.deliveryservice.exception.dto.DeliveryNotAvailabilityResponseDto;
import com.aston.homework.deliveryservice.service.DeliveryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryService deliveryService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String DELIVERY_SUCCESS_MESSAGE = "Order delivered successfully.";

    private static final String DELIVERY_FAILURE_MESSAGE = "Cannot deliver at the moment.";

    @Test
    void testDeliverSuccess() throws Exception {
        DeliveryResponseDto responseDto = new DeliveryResponseDto("orderId123", DELIVERY_SUCCESS_MESSAGE, DeliveryStatus.DELIVERED);

        when(deliveryService.deliverOrder(any(DeliveryRequestDto.class))).thenReturn(responseDto);

        DeliveryRequestDto requestDto = new DeliveryRequestDto("orderId123");

        mockMvc.perform(post("/api/delivery/{orderId}", requestDto.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("orderId123"))
                .andExpect(jsonPath("$.message").value(DELIVERY_SUCCESS_MESSAGE))
                .andExpect(jsonPath("$.status").value("DELIVERED"));
    }

    @Test
    void testDeliverFailure() throws Exception {
        DeliveryNotAvailabilityResponseDto errorResponse = new DeliveryNotAvailabilityResponseDto("orderId123", DELIVERY_FAILURE_MESSAGE, DeliveryStatus.NOT_DELIVERED);

        when(deliveryService.deliverOrder(any(DeliveryRequestDto.class)))
                .thenThrow(new DeliveryException(DELIVERY_FAILURE_MESSAGE, errorResponse));

        DeliveryRequestDto requestDto = new DeliveryRequestDto("orderId123");

        mockMvc.perform(post("/api/delivery/{orderId}", requestDto.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.orderId").value("orderId123"))
                .andExpect(jsonPath("$.status").value("NOT_DELIVERED"));
    }
}