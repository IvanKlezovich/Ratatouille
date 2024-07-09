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
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private static final String DELIVERY_SUCCESS_MESSAGE = "Order delivered successfully.";

    private final DeliveryRepository deliveryRepository;

    private final AtomicInteger requestCounter = new AtomicInteger();

    private final KafkaTemplate<String, Object> deliveryKafkaTemplate;

    @KafkaListener(topics = "delivery-request-topic", groupId = "delivery-group")
    public void handleDeliveryRequest(DeliveryRequestDto deliveryRequestDto) {
        DeliveryResponseDto deliveryResponse = new DeliveryResponseDto();
        deliveryResponse.setOrderId(deliveryRequestDto.getOrderId());
        boolean deliverySuccess = deliverOrder(deliveryRequestDto);

        if (deliverySuccess) {
            deliveryResponse.setStatus("DELIVERY_SUCCESS");
        } else {
            deliveryResponse.setStatus("DELIVERY_FAILED");
        }
        deliveryKafkaTemplate.send("delivery-response-topic", deliveryResponse);
    }

    public boolean deliverOrder(DeliveryRequestDto requestDto) {
        boolean canDeliver = checkDeliveryAvailability();

        Delivery delivery = DeliveryMapper.toEntity(requestDto);

        if (canDeliver) {
            delivery.setDeliveryStatus(DeliveryStatus.DELIVERED.toString());
            delivery.setMessage(DELIVERY_SUCCESS_MESSAGE);
            deliveryRepository.save(delivery);
            return true;
        } else {
            return false;
        }
    }

    boolean checkDeliveryAvailability() {
        return requestCounter.incrementAndGet() % 3 != 0;
    }

}
