package com.example.e_comerce.repositories;

import com.example.e_comerce.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.logging.LogManager;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
