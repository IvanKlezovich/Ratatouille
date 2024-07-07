package com.aston.homework.deliveryservice.repository;

import com.aston.homework.deliveryservice.data.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {

}

