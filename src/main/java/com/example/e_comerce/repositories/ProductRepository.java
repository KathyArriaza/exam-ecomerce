package com.example.e_comerce.repositories;

import com.example.e_comerce.entities.Products;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Products, Long> {
}
