package com.customer.CustomerService.models;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
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
                name ="mobile",
                columnNames = "mobile"
        )
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String mobile;
    private String emailId;
    private Date createdOn;
    private Date updatedOn;
    private boolean isActive;


}
