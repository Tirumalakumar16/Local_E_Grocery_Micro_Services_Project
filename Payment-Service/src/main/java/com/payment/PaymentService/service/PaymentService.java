package com.payment.PaymentService.service;

import com.payment.PaymentService.dtos.RequestPaymentDto;
import com.payment.PaymentService.dtos.ResponsePaymentDto;
import com.payment.PaymentService.exceptions.PaymentsNotFound;

import java.util.List;

public interface PaymentService {
    ResponsePaymentDto pay(RequestPaymentDto requestpaymentDto);

    List<ResponsePaymentDto> getAllPayments(String email) throws PaymentsNotFound;
}
