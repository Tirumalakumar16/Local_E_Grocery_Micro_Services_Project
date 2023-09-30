package com.orders.OrdersService.service;

import com.orders.OrdersService.dtos.RequestOrderDto;
import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.dtos.ResponseOrderShopDto;
import com.orders.OrdersService.dtos.ResponseOrdersShopTotalDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;

import java.util.List;

public interface OrderService {
    String orderFromCart(List<RequestOrderDto> requestOrderDto);

    List<ResponseOrderDto> getAllOrdersByShopName(String shopName) throws OrdersNotPlacedException;

    List<ResponseOrderDto> getAllOrdersByCustomerEmail(String customerEmail) throws OrdersNotPlacedException;

    List<ResponseOrderShopDto> getAllCustomersPerShopOrders(String shopName) throws OrdersNotPlacedException;

    List<ResponseOrdersShopTotalDto> getAllCustomersTotalAmountPerShopOrders(String shopName) throws OrdersNotPlacedException;
}
