package com.example.e_comerce.repositories;

import com.example.e_comerce.entities.Delivery;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    List<Delivery> findAll();
}
