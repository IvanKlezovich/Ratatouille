package org.example.restarauntservice.repository;

import org.example.restarauntservice.model.Dishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishesRepository extends JpaRepository<Dishes, Integer> {
}