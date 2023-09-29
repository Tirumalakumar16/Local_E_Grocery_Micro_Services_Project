package com.products.ProductService.dtos;

import com.products.ProductService.models.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseProductCustDto {

    private String productName;
    private String shopName;
    private int quantity;
    private double price;
    private ProductStatus available;
    private String emailId;

}
