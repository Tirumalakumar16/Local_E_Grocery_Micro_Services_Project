package com.shop.ShopService.dtos;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestShopDtos {

    private String shopName;
    private String mobile;
    private String ownerName;
    private String city;


}
