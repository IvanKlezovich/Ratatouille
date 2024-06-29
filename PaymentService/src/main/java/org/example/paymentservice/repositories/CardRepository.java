package org.example.paymentservice.repositories;

import org.example.paymentservice.entities.Card;
import org.springframework.data.repository.CrudRepository;

/**
 * The {@code CardRepository} interface is a Spring Data repository responsible for handling CRUD operations
 * for the {@link Card} entity. It extends the {@link CrudRepository} interface, which provides generic CRUD
 * operations for the entity class specified, in this case, the {@link Card} class with a primary key of type {@link Long}.
 * Additionally, it defines a custom method {@code findByNumber} to find a card by its number.
 */
public interface CardRepository extends CrudRepository<Card, Long> {

    /**
     * Finds a card by its number.
     *
     * @param cardNumber The number of the card to find.
     * @return The card with the specified number, or {@code null} if no such card exists.
     */
    Card findByNumber(Long cardNumber);
}
