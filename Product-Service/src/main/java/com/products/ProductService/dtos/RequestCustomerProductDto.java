package com.products.ProductService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestCustomerProductDto {

    private String productName;
    private int quantity;
    private String shopName;
}
