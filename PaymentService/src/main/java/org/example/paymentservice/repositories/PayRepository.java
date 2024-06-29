package org.example.paymentservice.repositories;

import org.example.paymentservice.entities.Pay;
import org.springframework.data.repository.CrudRepository;

/**
 * The {@code PayRepository} interface is a Spring Data repository responsible for handling CRUD operations
 * for the {@link Pay} entity. It extends the {@link CrudRepository} interface, which provides generic CRUD
 * operations for the entity class specified, in this case, the {@link Pay} class with a primary key of type {@link Long}.
 */
public interface PayRepository extends CrudRepository<Pay, Long> {
}
