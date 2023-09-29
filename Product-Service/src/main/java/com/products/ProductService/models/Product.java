package com.products.ProductService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String shopName;
    private int quantity;
    private double price;
    @Enumerated(EnumType.STRING)
    private ProductStatus available;
    private String emailId;
    private Date createdAt;
    private Date updatedAt;
    private String category;


}
