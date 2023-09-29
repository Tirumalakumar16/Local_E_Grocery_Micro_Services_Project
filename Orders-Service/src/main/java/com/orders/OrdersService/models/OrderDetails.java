package com.orders.OrdersService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
