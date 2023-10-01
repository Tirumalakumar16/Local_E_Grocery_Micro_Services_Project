package com.shop.ShopService.service;

import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.dtos.ResponseOrdersShopTotalDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.products.ProductService.dtos.RequestOwnerDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductAndSellerEmail;
import com.shop.ShopService.dtos.RequestShopDtos;
import com.shop.ShopService.dtos.ResponseShopCustDto;
import com.shop.ShopService.dtos.ResponseShopDto;
import com.orders.OrdersService.dtos.ResponseOrderShopDto;
import com.shop.ShopService.dtos.product.RequestProductShopDto;
import com.shop.ShopService.exceptions.ShopIsNotFoundException;
import com.shop.ShopService.exceptions.UserNotAutherizedException;
import com.shop.ShopService.exceptions.UserNotFound;

import java.util.List;

public interface ShopService {
    ResponseShopDto saveShop(RequestShopDtos requestShopDtos,String userName) throws UserNotFound;

    List<ResponseShopDto> getAll(String userName) throws UserNotAutherizedException, ShopIsNotFoundException;

    List<ResponseShopCustDto> findByCity(String city) throws ShopIsNotFoundException;

    ResponseProductDto saveProduct(RequestProductShopDto requestProductDto, String userName) throws UserNotFound, ShopIsNotFoundException;
    
    List<ResponseProductDto> getAllProducts(String userName) throws UserNotFound, ProductsNotAvailableWithProductAndSellerEmail;

    ResponseProductDto updateProduct(String userName, RequestOwnerDto requestOwnerDto) throws UserNotFound, UserNotAutherizedException, ProductsNotAvailableWithProductAndSellerEmail, ShopIsNotFoundException;

    List<ResponseOrderDto> getAllOrdersByOwnerEmailId(String userName) throws UserNotAutherizedException, OrdersNotPlacedException, ShopIsNotFoundException;

    List<ResponseOrderShopDto> getAllCustomersPerShopOrders(String userName) throws UserNotAutherizedException, OrdersNotPlacedException, ShopIsNotFoundException;

    List<ResponseOrdersShopTotalDto> getTotalAmountForEveryCustomer(String userName) throws UserNotAutherizedException, OrdersNotPlacedException, ShopIsNotFoundException;
}
