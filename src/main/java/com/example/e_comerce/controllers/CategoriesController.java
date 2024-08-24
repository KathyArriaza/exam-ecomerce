package com.example.e_comerce.controllers;

import com.example.e_comerce.Dto.CategoriesDto;
import com.example.e_comerce.Dto.CategoryDto;
import com.example.e_comerce.Dto.DeliveryDto;
import com.example.e_comerce.entities.Categories;
import com.example.e_comerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/categories")
public class CategoriesController {
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping
    public List<CategoriesDto> getAllCategories() {
        List<Categories> categoriesList = categoryRepository.findAll();
        return categoriesList.stream()
                .map(this::converToDto)
                .collect(Collectors.toList());
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriesDto> getCategoryById(@PathVariable Long id) {
        Optional<Categories> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(converToDto(category.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear una nueva categoría
    @PostMapping
    public CategoriesDto createCategory(@RequestBody CategoriesDto categoriesDto) {
        Categories category = convertToEntity(categoriesDto);
        Categories savedCategory = categoryRepository.save(category);
        return converToDto(savedCategory);
    }

    // Actualizar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<CategoriesDto> updateCategory(@PathVariable Long id, @RequestBody CategoriesDto categoriesDto) {
        Optional<Categories> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Categories updatedCategory = category.get();
            updatedCategory.setCategoryName(categoriesDto.getCategoryName());
            updatedCategory.setCategoryType(categoriesDto.getCategoryType());
            Categories savedCategory = categoryRepository.save(updatedCategory);
            return ResponseEntity.ok(converToDto(savedCategory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Optional<Categories> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    //Metodos de conversion
   private CategoriesDto converToDto (Categories categories){
       CategoriesDto categoriesDto = new CategoriesDto();
      categoriesDto.setId(categories.getId());
       categoriesDto.setCategoryName(categories.getCategoryName());
              categoriesDto.setCategoryType(categories.getCategoryType());
       return categoriesDto;
    }

   private Categories convertToEntity(CategoriesDto categoriesDto) {
       Categories categories = new Categories();
       categories.setCategoryName(categoriesDto.getCategoryName());
       categories.setCategoryType(categoriesDto.getCategoryType());
       return categories;
   }


}
