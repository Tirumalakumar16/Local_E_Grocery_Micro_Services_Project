package com.shop.ShopService.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalIdCache;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private String errorMessage;
}
