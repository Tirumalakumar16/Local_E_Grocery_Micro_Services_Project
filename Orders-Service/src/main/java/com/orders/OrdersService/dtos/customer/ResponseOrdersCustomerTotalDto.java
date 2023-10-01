package com.orders.OrdersService.dtos.customer;

import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseOrdersCustomerTotalDto {

    private String customerName;
    private String customerEmailId;
    private String shopName;
    @Transient
    private double total;

    public ResponseOrdersCustomerTotalDto(String customerName, String customerEmailId, String shopName, double total) {
        this.customerName = customerName;
        this.customerEmailId = customerEmailId;
        this.shopName = shopName;
        this.total = total;
    }
}
