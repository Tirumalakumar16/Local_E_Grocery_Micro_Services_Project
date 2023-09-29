package com.shop.ShopService.controller;

import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.shop.ShopService.exceptions.ShopIsNotFoundException;
import com.shop.ShopService.exceptions.UserNotAutherizedException;
import com.shop.ShopService.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {


    private ShopService shopService;
    @Autowired
    public OrderController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/shop/orders")
    public List<ResponseOrderDto> getAllOrdersByOwnerEmailId(@RequestHeader("LoggedInUser") String  userName) throws UserNotAutherizedException, OrdersNotPlacedException, ShopIsNotFoundException {

        return shopService.getAllOrdersByOwnerEmailId(userName);
    }
}
