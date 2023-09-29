package com.payment.PaymentService.dtos;

import com.payment.PaymentService.models.PaymentMode;
import com.payment.PaymentService.models.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPaymentDto {


    private String customerName;
    private String customerEmailId;
    private double totalAmount;
    private PaymentMode paymentMode;

}
