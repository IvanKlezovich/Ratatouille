package org.example.paymentservice.repositories;

import org.example.paymentservice.entities.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Long> {
    Card findByNumber(Long cardNumber);
}
