package com.orders.OrdersService.controller;

import com.orders.OrdersService.dtos.RequestOrderDto;
import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.orders.OrdersService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public String orderFromCart(@RequestBody List<RequestOrderDto> requestOrderDto) {

         return orderService.orderFromCart(requestOrderDto);

    }

    @GetMapping("/orders/shop/{shopName}")
    public List<ResponseOrderDto> getAllOrdersByShopName(@PathVariable("shopName") String shopName) throws OrdersNotPlacedException {

        return orderService.getAllOrdersByShopName(shopName);
    }

    @GetMapping("/orders/customer/{customerEmail}")
    public List<ResponseOrderDto> getAllOrdersByCustomerEmail(@PathVariable("customerEmail") String customerEmail) throws OrdersNotPlacedException {

        return orderService.getAllOrdersByCustomerEmail(customerEmail);
    }
}
