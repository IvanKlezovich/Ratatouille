package org.example.orderservice.repository;

import org.example.orderservice.model.Dishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishesRepository extends JpaRepository<Dishes, Integer> {
}