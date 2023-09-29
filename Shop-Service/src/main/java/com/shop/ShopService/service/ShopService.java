package com.shop.ShopService.service;

import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.products.ProductService.dtos.RequestOwnerDto;
import com.products.ProductService.dtos.RequestProductDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.shop.ShopService.dtos.RequestShopDtos;
import com.shop.ShopService.dtos.ResponseShopCustDto;
import com.shop.ShopService.dtos.ResponseShopDto;
import com.shop.ShopService.dtos.product.RequestProductShopDto;
import com.shop.ShopService.exceptions.ShopIsNotFoundException;
import com.shop.ShopService.exceptions.UserNotAutherizedException;
import com.shop.ShopService.exceptions.UserNotFound;

import java.util.List;

public interface ShopService {
    ResponseShopDto saveShop(RequestShopDtos requestShopDtos,String userName) throws UserNotFound;

    List<ResponseShopDto> getAll(String userName) throws  UserNotAutherizedException;

    List<ResponseShopCustDto> findByCity(String city);

    ResponseProductDto saveProduct(RequestProductShopDto requestProductDto, String userName) throws UserNotFound;
    
    List<ResponseProductDto> getAllProducts(String userName) throws UserNotFound;

    ResponseProductDto updateProduct(String userName, RequestOwnerDto requestOwnerDto) throws UserNotFound, UserNotAutherizedException;

    List<ResponseOrderDto> getAllOrdersByOwnerEmailId(String userName) throws UserNotAutherizedException, OrdersNotPlacedException, ShopIsNotFoundException;
}
