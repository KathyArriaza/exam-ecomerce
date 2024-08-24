package com.example.e_comerce.entities;

import com.example.e_comerce.Dto.CategoryDto;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name= "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    @ManyToMany(mappedBy = "products")
    private Set<Order> orders;




    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
