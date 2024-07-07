package com.aston.homework.deliveryservice.service.impl;

import com.aston.homework.deliveryservice.data.constant.DeliveryStatus;
import com.aston.homework.deliveryservice.data.entity.Delivery;
import com.aston.homework.deliveryservice.dto.request.DeliveryRequestDto;
import com.aston.homework.deliveryservice.dto.response.DeliveryResponseDto;
import com.aston.homework.deliveryservice.exception.DeliveryException;
import com.aston.homework.deliveryservice.repository.DeliveryRepository;
import com.aston.homework.deliveryservice.service.impl.DeliveryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @Captor
    private ArgumentCaptor<Delivery> deliveryCaptor;

    private DeliveryRequestDto requestDto;

    private static final String DELIVERY_SUCCESS_MESSAGE = "Order delivered successfully.";

    private static final String DELIVERY_FAILURE_MESSAGE = "Cannot deliver at the moment.";

    @BeforeEach
    void setUp() {
        requestDto = new DeliveryRequestDto("orderId123");
    }

    @Test
    void testDeliverOrderSuccess() {
        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DeliveryResponseDto response = deliveryService.deliverOrder(requestDto);

        assertEquals(DeliveryStatus.DELIVERED, response.getStatus());
        assertEquals(DELIVERY_SUCCESS_MESSAGE, response.getMessage());

        verify(deliveryRepository, times(1)).save(deliveryCaptor.capture());
        Delivery savedDelivery = deliveryCaptor.getValue();
        assertEquals(DeliveryStatus.DELIVERED, savedDelivery.getDeliveryStatus());
        assertEquals(DELIVERY_SUCCESS_MESSAGE, savedDelivery.getMessage());
    }

    @Test
    void testDeliverOrderFailure() {
        deliveryService = spy(deliveryService);
        doReturn(false).when(deliveryService).checkDeliveryAvailability();

        DeliveryException exception = assertThrows(DeliveryException.class, () -> deliveryService.deliverOrder(requestDto));

        assertEquals(DELIVERY_FAILURE_MESSAGE, exception.getMessage());
        assertNotNull(exception.getResponse());
        assertEquals(DeliveryStatus.NOT_DELIVERED, exception.getResponse().getStatus());

        verify(deliveryRepository, times(1)).save(deliveryCaptor.capture());
        Delivery savedDelivery = deliveryCaptor.getValue();
        assertEquals(DeliveryStatus.NOT_DELIVERED, savedDelivery.getDeliveryStatus());
        assertEquals(DELIVERY_FAILURE_MESSAGE, savedDelivery.getMessage());
    }
}