package com.payment.PaymentService.controller;

import com.payment.PaymentService.dtos.RequestPaymentDto;
import com.payment.PaymentService.dtos.ResponsePaymentDto;
import com.payment.PaymentService.exceptions.PaymentsNotFound;
import com.payment.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PaymentController {

    private PaymentService paymentService;
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/payment")
    public ResponsePaymentDto pay(@RequestBody RequestPaymentDto requestpaymentDto) {

        return paymentService.pay(requestpaymentDto);
    }

    @GetMapping("/payment/{email}")
    public List<ResponsePaymentDto> getAllPayments(@PathVariable("email") String email) throws PaymentsNotFound {

        return paymentService.getAllPayments(email);
    }
}
