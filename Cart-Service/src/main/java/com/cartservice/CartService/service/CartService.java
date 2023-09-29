package com.cartservice.CartService.service;

import com.cartservice.CartService.dtos.RequestCartDto;
import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.dtos.UpdateCartDto;
import com.cartservice.CartService.exceptions.CartDetailsNotFound;

import java.util.List;

public interface CartService {
    ResponseCartDto saveCart(RequestCartDto requestCartDto);

    List<ResponseCartDto> getAllCart(String email) throws CartDetailsNotFound;

    ResponseCartDto updateCart(UpdateCartDto updateCartDto) throws CartDetailsNotFound;

    void delete(String product, String email);

    void deleteEmail(String email);
}
