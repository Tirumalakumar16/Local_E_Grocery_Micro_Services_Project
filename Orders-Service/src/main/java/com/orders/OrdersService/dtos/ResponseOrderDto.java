package com.orders.OrdersService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseOrderDto {

    private String shopName;
    private String productName;
    private int quantity;
    private double price;
    private String customerEmailId;
    private String customerName;
    private Date createdAt;
    private String transactionId;
    private String houseNumber;
    private String zip;
}
