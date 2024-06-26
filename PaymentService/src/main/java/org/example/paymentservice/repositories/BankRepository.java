package org.example.paymentservice.repositories;

import org.example.paymentservice.entities.Bank;
import org.springframework.data.repository.CrudRepository;

public interface BankRepository extends CrudRepository<Bank, Long> {
    Bank getBankByNumberCard(Long cardId);
}
