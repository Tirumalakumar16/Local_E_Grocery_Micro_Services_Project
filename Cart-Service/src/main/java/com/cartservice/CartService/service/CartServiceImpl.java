package com.cartservice.CartService.service;

import com.cartservice.CartService.dtos.RequestCartDto;
import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.dtos.UpdateCartDto;
import com.cartservice.CartService.exceptions.CartDetailsNotFound;
import com.cartservice.CartService.models.Cart;
import com.cartservice.CartService.repository.CartRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    private CartRepository cartRepository;

    private ModelMapper modelMapper;
    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseCartDto saveCart(RequestCartDto requestCartDto) {

        Cart cart = mapToCart(requestCartDto);
        Cart cart1 = cartRepository.save(cart);

        return modelMapper.map(cart1, ResponseCartDto.class);
    }

    private Cart mapToCart(RequestCartDto requestCartDto) {
        Cart cart = new Cart();
        cart.setCreatedAt(new Date());
        cart.setPrice(requestCartDto.getPrice());
        cart.setQuantity(requestCartDto.getQuantity());
        cart.setEmailId(requestCartDto.getEmailId());
        cart.setProductName(requestCartDto.getProductName());
        cart.setUpdatedOn(new Date());
        cart.setShopName(requestCartDto.getShopName());
        return cart;
    }

    @Override
    public List<ResponseCartDto> getAllCart(String email) throws CartDetailsNotFound {

        List<Cart> carts = cartRepository.findByEmailId(email);

        if (carts.isEmpty()) {
            throw new CartDetailsNotFound("cart Details Not found with email "+ email);
        }

        return Arrays.asList(modelMapper.map(carts,ResponseCartDto[].class));
    }

    @Override
    public ResponseCartDto updateCart(UpdateCartDto updateCartDto) throws CartDetailsNotFound {
        Cart cart = cartRepository.findByEmailIdAndProductName(updateCartDto.getEmailId(),updateCartDto.getProductName());

        if (cart == null) {
            throw new CartDetailsNotFound("Please add product to cart first then edit....");
        }
        cart.setQuantity(updateCartDto.getQuantity());
        Cart cart1 = cartRepository.save(cart);
        return modelMapper.map(cart1, ResponseCartDto.class);
    }

    @Override
    public void delete(String product, String email) {
        cartRepository.deleteByEmailAndProductName(email,product);
    }

    @Override
    public void deleteEmail(String email) {
        cartRepository.deleteByEmailId(email);
    }
}
