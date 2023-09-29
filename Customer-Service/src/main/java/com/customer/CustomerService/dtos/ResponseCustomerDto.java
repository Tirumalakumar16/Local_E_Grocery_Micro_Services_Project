package com.customer.CustomerService.dtos;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ResponseCustomerDto {

    private String customerName;
    private String mobile;
    private String emailId;
    private Date createdOn;
    private Date updatedOn;
    private boolean isActive;
}
