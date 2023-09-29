package com.cartservice.CartService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCartDto {
    private String shopName;
    private String productName;
    private int quantity;
    private double price;
    private String emailId;

}
