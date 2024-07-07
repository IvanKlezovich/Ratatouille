package org.example.paymentservice.repository;

import org.example.paymentservice.data.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay, Long> {
    void deleteByOrderId(Long orderId);
    Pay findByOrderId(Long orderId);
}
