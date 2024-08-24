package com.example.e_comerce.controllers;

import com.example.e_comerce.Dto.ProductDto;
import com.example.e_comerce.entities.Categories;
import com.example.e_comerce.entities.Products;
import com.example.e_comerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
    private ProductRepository productRepository;


  @GetMapping
  public List<ProductDto> getAllProducts() { // GET http://localhost:8080/api/products
    List<Products> products = (List<Products>) productRepository.findAll();
    List<ProductDto> productDtos = products.stream().map(product -> {
      ProductDto productDto = new ProductDto();
      productDto.setId(product.getId());
      productDto.setProductName(product.getProductName());
      productDto.setCategory(product.getCategory());
      return productDto;
    }).toList();
    return productDtos;
  }

 @GetMapping("/{id}")
 public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
   Optional<Products> product = productRepository.findById(id);
   if (product.isPresent()) {
     ProductDto productDto = new ProductDto();
     productDto.setId(product.get().getId());
     productDto.setProductName(product.get().getProductName()); // Esto está bien
     productDto.setCategory(product.get().getCategory());
     return ResponseEntity.ok(productDto);
   } else {
     return ResponseEntity.notFound().build();
   }
 }



@PostMapping
  public ProductDto createProduct(@RequestBody Products product) { // POST http://localhost:8080/api/products + JSON
    Products newProduct = productRepository.save(product);
    ProductDto productDto = new ProductDto();
    productDto.setId(newProduct.getId());
    productDto.setProductName(newProduct.getProductName());
    productDto.setCategory(newProduct.getCategory());
    return productDto;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Products> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDetails) {
    Optional<Products> product = productRepository.findById(id);
    if (product.isPresent()) {
      Products updatedProduct = product.get();
      updatedProduct.setProductName(productDetails.getProductName());

      Categories category = new Categories();
      category.setId(productDetails.getCategory().getId());
      category.setCategoryName(productDetails.getCategory().getCategoryName());
      category.setCategoryType(productDetails.getCategory().getCategoryType());

      updatedProduct.setCategory(category);
      return ResponseEntity.ok(productRepository.save(updatedProduct));
    } else {
      return ResponseEntity.notFound().build();
    }
  }


     @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) { // DELETE http://localhost:8080/api/products/{id}
    Optional<Products> product = productRepository.findById(id);
    if (product.isPresent()) {
      productRepository.delete(product.get());
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
