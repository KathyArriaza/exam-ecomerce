package com.example.e_comerce.repositories;

import com.example.e_comerce.entities.Categories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Categories, Long> {
    List<Categories> findAll();
}
