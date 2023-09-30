package com.orders.OrdersService.dtos;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
public class ResponseOrderShopDto {

    private Date createdAt;
    private String customerName;
    private String customerEmailId;
    private String houseNumber;
    private String shopName;
    @Transient
    private double total;

    public ResponseOrderShopDto() {
    }

    public ResponseOrderShopDto(Date createdAt, String customerName, String customerEmailId, String houseNumber, String shopName, double total) {
        this.createdAt = createdAt;
        this.customerName = customerName;
        this.customerEmailId = customerEmailId;
        this.houseNumber = houseNumber;
        this.shopName = shopName;
        this.total = total;
    }
}
