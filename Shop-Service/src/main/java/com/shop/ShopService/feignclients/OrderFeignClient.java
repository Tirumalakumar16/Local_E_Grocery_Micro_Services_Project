package com.shop.ShopService.feignclients;

import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.dtos.ResponseOrderShopDto;
import com.orders.OrdersService.dtos.ResponseOrdersShopTotalDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Order-Service",path = "/www.localGrocery.com/order/api")
public interface OrderFeignClient {

    @GetMapping("/orders/shop/total/{shopName}")
    public List<ResponseOrderShopDto> getAllCustomersPerShopOrders(@PathVariable("shopName") String shopName) throws OrdersNotPlacedException;
    @GetMapping("/orders/shop/{shopName}")
    public List<ResponseOrderDto> getAllOrdersByShopName(@PathVariable("shopName") String shopName) throws OrdersNotPlacedException;

    @GetMapping("/orders/shop/total/amount/{shopName}")
    public List<ResponseOrdersShopTotalDto> getAllCustomersTotalAmountPerShopOrders(@PathVariable("shopName") String shopName) throws OrdersNotPlacedException;


}
