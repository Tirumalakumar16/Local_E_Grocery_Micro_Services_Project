package com.orders.OrdersService.feignclients;

import com.payment.PaymentService.dtos.RequestPaymentDto;
import com.payment.PaymentService.dtos.ResponsePaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Payment-Service" , path = "/www.localGrocery.com/payment/api")
public interface PaymentFeignClient {

    @PostMapping("/payment")
    public ResponsePaymentDto pay(@RequestBody RequestPaymentDto requestpaymentDto);



}
