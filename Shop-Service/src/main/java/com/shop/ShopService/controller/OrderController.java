package com.shop.ShopService.controller;

import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.dtos.ResponseOrdersShopTotalDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.orders.OrdersService.dtos.ResponseOrderShopDto;
import com.shop.ShopService.exceptions.ShopIsNotFoundException;
import com.shop.ShopService.exceptions.UserNotAutherizedException;
import com.shop.ShopService.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:5173/")
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



    //Based on particular date how many orders were placed by customer with total and address
    @GetMapping("/shop/orders/customers")
    public List<ResponseOrderShopDto> getAllCustomersPerShopOrders(@RequestHeader("LoggedInUser") String userName) throws UserNotAutherizedException, OrdersNotPlacedException, ShopIsNotFoundException {

        return  shopService.getAllCustomersPerShopOrders(userName);
    }


    @GetMapping("/shop/orders/customers/total")
    public List<ResponseOrdersShopTotalDto> getTotalAmountForEveryCustomer(@RequestHeader("LoggedInUser") String userName) throws UserNotAutherizedException, OrdersNotPlacedException, ShopIsNotFoundException {

        return shopService.getTotalAmountForEveryCustomer(userName);
    }

}
