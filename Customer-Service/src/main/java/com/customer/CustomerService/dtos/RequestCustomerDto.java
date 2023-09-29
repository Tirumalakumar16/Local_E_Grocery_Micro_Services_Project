package com.customer.CustomerService.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RequestCustomerDto {

    private String customerName;
    private String mobile;
}
