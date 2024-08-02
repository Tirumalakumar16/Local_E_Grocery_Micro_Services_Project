package com.customer.CustomerService.dtos;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ResponseCustomerDto {
    private Long id;
    private String customerName;
    private String mobile;
    private String emailId;

    private boolean isActive;
    private String username;
}
