package com.cartservice.CartService.service;

import com.cartservice.CartService.config.KafkaPublisherClient;
import com.cartservice.CartService.dtos.KafkaMessage;
import com.cartservice.CartService.dtos.RequestCartDto;
import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.dtos.UpdateCartDto;
import com.cartservice.CartService.exceptions.CartDetailsNotFound;
import com.cartservice.CartService.models.Cart;
import com.cartservice.CartService.repository.CartRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private KafkaPublisherClient kafkaPublisherClient;
    private ObjectMapper objectMapper;
    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ModelMapper modelMapper, KafkaPublisherClient kafkaPublisherClient, ObjectMapper objectMapper) {
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
        this.kafkaPublisherClient = kafkaPublisherClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseCartDto saveCart(RequestCartDto requestCartDto) {

        Cart cart = mapToCart(requestCartDto);
        Cart cart1 = cartRepository.save(cart);

        // kafka message to send email to user for product added to cart
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setFrom("grocerystore4169@gmail.com");
        kafkaMessage.setSubject("Product added to Cart");
        kafkaMessage.setTo(requestCartDto.getEmailId());
        kafkaMessage.setBody("\n\n\n" +
                ""+requestCartDto.getProductName()+" is added to your cart ....\n\n" +
                "get Product by Order Now ... Hurry up!!! \n\n\n"+
                "Team Grocery Store");
        try {
            kafkaPublisherClient.sendMessage("productAddedToCart", objectMapper.writeValueAsString(kafkaMessage));
        } catch (Exception e) {
            e.getStackTrace();
        }

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
