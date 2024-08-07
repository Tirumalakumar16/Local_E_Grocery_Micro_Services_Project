package com.orders.OrdersService.controller;

import com.orders.OrdersService.dtos.RequestOrderDto;
import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.dtos.ResponseOrderShopDto;
import com.orders.OrdersService.dtos.ResponseOrdersShopTotalDto;
import com.orders.OrdersService.dtos.customer.ResponseOrderCustomerDateDto;
import com.orders.OrdersService.dtos.customer.ResponseOrdersCustomerTotalDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.orders.OrdersService.exceptions.PaymentFailedException;
import com.orders.OrdersService.service.OrderService;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:5173/")
@RestController
public class OrderController {

    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public String orderFromCart(@RequestBody List<RequestOrderDto> requestOrderDto) throws PaymentFailedException, OrdersNotPlacedException, ProductsNotAvailableWithProductName {

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



    @GetMapping("/orders/shop/total/{shopName}")
    public List<ResponseOrderShopDto> getAllCustomersPerShopOrders(@PathVariable("shopName") String shopName) throws OrdersNotPlacedException {

        return orderService.getAllCustomersPerShopOrders(shopName);
    }

    @GetMapping("/orders/shop/total/amount/{shopName}")
    public List<ResponseOrdersShopTotalDto> getAllCustomersTotalAmountPerShopOrders(@PathVariable("shopName") String shopName) throws OrdersNotPlacedException {

        return orderService.getAllCustomersTotalAmountPerShopOrders(shopName);
    }

    @GetMapping("/orders/customer/total/amount/{email}")
    public List<ResponseOrdersCustomerTotalDto> getAllCustomersTotalAmountPerShop(@PathVariable("email") String email) throws OrdersNotPlacedException {

        return orderService.getAllCustomersTotalAmountPerShop(email);
    }

    @GetMapping("/orders/customer/onDate/{email}")
    public List<ResponseOrderCustomerDateDto> getAllCustomerOrdersOnDate(@PathVariable("email") String email) throws OrdersNotPlacedException {

        return orderService.getAllCustomerOrdersOnDate(email);
    }
}
