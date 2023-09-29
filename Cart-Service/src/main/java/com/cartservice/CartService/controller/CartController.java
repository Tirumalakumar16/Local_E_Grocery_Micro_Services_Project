package com.cartservice.CartService.controller;

import com.cartservice.CartService.dtos.RequestCartDto;
import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.dtos.UpdateCartDto;
import com.cartservice.CartService.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    private CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/cart")
    public ResponseEntity<ResponseCartDto> saveCart(@RequestBody RequestCartDto requestCartDto) {

        ResponseCartDto responseCartDto = cartService.saveCart(requestCartDto);

        return new ResponseEntity<>(responseCartDto, HttpStatus.CREATED);
    }
    @GetMapping("/carts/{email}")
    public List<ResponseCartDto> getAllCart(@PathVariable("email") String email) {

        return cartService.getAllCart(email);
    }
    @PutMapping("/cart")
    public ResponseEntity<ResponseCartDto> updatecart(@RequestBody UpdateCartDto updateCartDto) {

        ResponseCartDto responseCartDto = cartService.updateCart(updateCartDto);

        return new ResponseEntity<>(responseCartDto,HttpStatus.OK);
    }

    @DeleteMapping("/cart/{product}/{email}")
    public ResponseEntity<String> deleteCartProduct( @PathVariable("product") String product,@PathVariable("email") String email) {
        // Deleting the entire cart with having email cart service.
        cartService.delete(product,email);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted from Cart...!");
    }

    @DeleteMapping("/cart/{email}")
    public void deleteCartProducts(@PathVariable("email") String email) {
        // Deleting the entire cart with having email cart service.
        cartService.deleteEmail(email);

    }

}
