package com.example.e_comerce.repositories;

import com.example.e_comerce.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
List<Customer> findAll();
}
