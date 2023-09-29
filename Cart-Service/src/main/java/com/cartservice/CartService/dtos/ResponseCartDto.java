package com.cartservice.CartService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCartDto {

    private String shopName;
    private String productName;
    private int quantity;
    private double price;
    private String emailId;
    private Date createdAt;
    private Date updatedOn;
}
