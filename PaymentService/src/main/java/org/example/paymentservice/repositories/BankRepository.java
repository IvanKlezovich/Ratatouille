package org.example.paymentservice.repositories;

import org.example.paymentservice.entities.Bank;
import org.springframework.data.repository.CrudRepository;

/**
 * The {@code BankRepository} interface is a Spring Data repository responsible for handling CRUD operations
 * for the {@link Bank} entity. It extends the {@link CrudRepository} interface, which provides generic CRUD
 * operations for the entity class specified, in this case, the {@link Bank} class with a primary key of type {@link Long}.
 * Additionally, it defines a custom method {@code getBankByNumberCard} to retrieve a bank entity by its associated card number.
 */
public interface BankRepository extends CrudRepository<Bank, Long> {
    /**
     * Retrieves a bank entity by its associated card number.
     *
     * @param cardId The card number associated with the bank account.
     * @return The bank entity with the specified card number, or {@code null} if no such bank entity exists.
     */
    Bank getBankByNumberCard(Long cardId);
}
