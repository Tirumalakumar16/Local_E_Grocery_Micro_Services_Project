package com.shop.ShopService.dtos;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseShopDto {

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
