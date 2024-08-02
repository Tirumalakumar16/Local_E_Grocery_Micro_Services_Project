package com.products.ProductService.dtos;

import com.products.ProductService.models.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseProductDto {
    private Long id;
    private String productName;
    private String shopName;
    private int quantity;
    private double price;
    private String category;
    private ProductStatus available;
    private String emailId;
    private Date createdAt;
    private Date updatedAt;
}
