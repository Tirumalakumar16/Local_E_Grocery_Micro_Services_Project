package com.cartservice.CartService.service;

import com.cartservice.CartService.dtos.RequestCartDto;
import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.dtos.UpdateCartDto;

import java.util.List;

public interface CartService {
    ResponseCartDto saveCart(RequestCartDto requestCartDto);

    List<ResponseCartDto> getAllCart(String email);

    ResponseCartDto updateCart(UpdateCartDto updateCartDto);

    void delete(String product, String email);

    void deleteEmail(String email);
}
