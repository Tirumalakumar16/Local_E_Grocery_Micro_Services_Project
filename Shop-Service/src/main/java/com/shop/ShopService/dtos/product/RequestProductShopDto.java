package com.shop.ShopService.dtos.product;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestProductShopDto {


    private String productName;
    private int quantity;
    private double price;
    private String category;
}
