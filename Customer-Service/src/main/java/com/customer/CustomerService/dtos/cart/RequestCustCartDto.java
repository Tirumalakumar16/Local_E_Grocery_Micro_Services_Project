package com.customer.CustomerService.dtos.cart;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RequestCustCartDto {
    private String productName;
}
