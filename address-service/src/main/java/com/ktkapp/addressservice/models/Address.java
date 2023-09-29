package com.ktkapp.addressservice.models;

import brave.internal.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(
        name = "address",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "houseNumber_uk",
                        columnNames = "houseNumber"
                )
        }
)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String houseNumber;
    private String landMark;
    private String city;
    private String emailId;
    private String district;
    @Column(nullable = false)
    private String zip;
    private String state;
    private String streetName;
    private Date createdAt;
}
