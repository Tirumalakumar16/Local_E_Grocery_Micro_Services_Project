package com.orders.OrdersService.dtos.customer;

import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResponseOrderCustomerDateDto {

    private Date createdAt;
    private String customerName;
    private String customerEmailId;
    private String houseNumber;
    private String shopName;
    @Transient
    private double total;

    private String transactionId;

    public ResponseOrderCustomerDateDto(Date createdAt, String customerName, String customerEmailId, String houseNumber, String shopName, double total, String transactionId) {
        this.createdAt = createdAt;
        this.customerName = customerName;
        this.customerEmailId = customerEmailId;
        this.houseNumber = houseNumber;
        this.shopName = shopName;
        this.total = total;
        this.transactionId = transactionId;
    }
}
