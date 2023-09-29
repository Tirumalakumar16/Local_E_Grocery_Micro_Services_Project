package com.shop.ShopService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( uniqueConstraints = {
        @UniqueConstraint(
                name = "shopName",
                columnNames = "shopName"
        ),
        @UniqueConstraint(
                name ="emailId",
                columnNames = "emailId"
        )
})
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shopName;
    private String mobile;
    private String emailId;
    private String ownerName;
    private Date registeredOn;
    private Date updatedOn;
    private boolean isActive;
    private int productsRegistered;
    private String city;

}
