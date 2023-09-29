package com.products.ProductService.dtos;

import com.products.ProductService.models.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestProductDto {

    private String productName;
    private String shopName;
    private int quantity;
    private double price;
    private String emailId;
    private String category;

}
