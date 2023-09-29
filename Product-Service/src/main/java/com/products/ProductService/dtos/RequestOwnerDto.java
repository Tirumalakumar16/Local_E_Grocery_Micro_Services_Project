package com.products.ProductService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestOwnerDto {
    private String productName;
    private String shopName;
    private int quantity;
    private double price;
    private String emailId;
    private String category;
}
