package com.shop.ShopService.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseShopCustDto {

    private String shopName;
    private String mobile;
    private String emailId;
    private String ownerName;
    private String city;

}
