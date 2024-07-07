package com.aston.homework.deliveryservice.service.impl;

import com.aston.homework.deliveryservice.data.constant.DeliveryStatus;
import com.aston.homework.deliveryservice.data.entity.Delivery;
import com.aston.homework.deliveryservice.dto.request.DeliveryRequestDto;
import com.aston.homework.deliveryservice.dto.response.DeliveryResponseDto;
import com.aston.homework.deliveryservice.exception.DeliveryException;
import com.aston.homework.deliveryservice.exception.dto.DeliveryNotAvailabilityResponseDto;
import com.aston.homework.deliveryservice.repository.DeliveryRepository;
import com.aston.homework.deliveryservice.service.DeliveryService;
import com.aston.homework.deliveryservice.utils.DeliveryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private static final String DELIVERY_SUCCESS_MESSAGE = "Order delivered successfully.";
    private static final String DELIVERY_FAILURE_MESSAGE = "Cannot deliver at the moment.";

    private final DeliveryRepository deliveryRepository;

    private final AtomicInteger requestCounter = new AtomicInteger();

    @Transactional
    public DeliveryResponseDto deliverOrder(DeliveryRequestDto requestDto) {
        boolean canDeliver = checkDeliveryAvailability();

        Delivery delivery = DeliveryMapper.toEntity(requestDto);

        if (canDeliver) {
            delivery.setDeliveryStatus(DeliveryStatus.DELIVERED);
            delivery.setMessage(DELIVERY_SUCCESS_MESSAGE);
        } else {
            delivery.setDeliveryStatus(DeliveryStatus.NOT_DELIVERED);
            delivery.setMessage(DELIVERY_FAILURE_MESSAGE);

            DeliveryNotAvailabilityResponseDto response = new DeliveryNotAvailabilityResponseDto();
            response.setOrderId(requestDto.getOrderId());
            response.setMessage(delivery.getMessage());
            response.setStatus(delivery.getDeliveryStatus());

            deliveryRepository.save(delivery);
            throw new DeliveryException(DELIVERY_FAILURE_MESSAGE, response);
        }

        Delivery savedDelivery = deliveryRepository.save(delivery);
        return DeliveryMapper.toDto(savedDelivery);
    }

    boolean checkDeliveryAvailability() {
        return requestCounter.incrementAndGet() % 3 != 0;
    }

}
