package com.customer.CustomerService.feignclients;

import com.orders.OrdersService.dtos.RequestOrderDto;
import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.dtos.customer.ResponseOrderCustomerDateDto;
import com.orders.OrdersService.dtos.customer.ResponseOrdersCustomerTotalDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@FeignClient(name = "Order-Service",path = "/www.localGrocery.com/order/api")
public interface OrderFeignClient {


    @GetMapping("/orders/customer/total/amount/{email}")
    public List<ResponseOrdersCustomerTotalDto> getAllCustomersTotalAmountPerShop(@PathVariable("email") String email) throws OrdersNotPlacedException;
    @PostMapping("/order")
    public String orderFromCart(@RequestBody List<RequestOrderDto> requestOrderDto);

    @GetMapping("/orders/customer/{customerEmail}")
    public List<ResponseOrderDto> getAllOrdersByCustomerEmail(@PathVariable("customerEmail") String customerEmail) throws OrdersNotPlacedException;

    @GetMapping("/orders/customer/onDate/{email}")
    public List<ResponseOrderCustomerDateDto> getAllCustomerOrdersOnDate(@PathVariable("email") String email) throws OrdersNotPlacedException;




}
