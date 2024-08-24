package com.example.e_comerce.Dto;

public class CategoriesDto {
    private Long id;

    private String categoryName;
    private String categoryType;

    public CategoriesDto(){

    }

    public CategoriesDto(String categoryName, String categoryType, Long id) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
