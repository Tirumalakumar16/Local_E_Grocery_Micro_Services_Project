package com.customer.CustomerService.feignclients;

import com.payment.PaymentService.dtos.ResponsePaymentDto;
import com.payment.PaymentService.exceptions.PaymentsNotFound;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Payment-Service" ,path = "/www.localGrocery.com/payment/api")
public interface PaymentFeignClient {

    @GetMapping("/payment/{email}")
    public List<ResponsePaymentDto> getAllPayments(@PathVariable("email") String email) throws PaymentsNotFound;
}
