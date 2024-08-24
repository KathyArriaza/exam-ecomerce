package com.example.e_comerce.Dto;

import com.example.e_comerce.entities.Products;

public class ProductDto {
     private Long id;
    private String productName;

    private CategoryDto category;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setCategory(CategoryDto category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setCategoryType(category.getCategoryType());
        this.category = categoryDto;
    }


    @Override
    public String toString() {
        return "{ id=" + id +
                ", productName='" + productName +
                "category=" + category +
                '}';

    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        ProductDto that = (ProductDto) o;
//
//        if (!id.equals(that.id)) return false;
//        if (!productName.equals(that.productName)) return false;
//        return category.equals(that.category);
//    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + productName.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }
}
