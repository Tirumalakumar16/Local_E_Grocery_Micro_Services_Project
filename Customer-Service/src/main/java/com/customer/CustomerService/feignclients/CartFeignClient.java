package com.customer.CustomerService.feignclients;

import com.cartservice.CartService.dtos.RequestCartDto;
import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.dtos.UpdateCartDto;
import com.cartservice.CartService.exceptions.CartDetailsNotFound;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Cart-Service",path = "/www.localGrocery.com/cart/api")
public interface CartFeignClient {

    @PostMapping("/cart")
    public ResponseEntity<ResponseCartDto> saveCart(@RequestBody RequestCartDto requestCartDto);

    @GetMapping("/carts/{email}")
    public List<ResponseCartDto> getAllCart(@PathVariable("email") String email) throws CartDetailsNotFound;

    @PutMapping("/cart")
    public ResponseEntity<ResponseCartDto> updatecart(@RequestBody UpdateCartDto updateCartDto) throws CartDetailsNotFound;

    @DeleteMapping("/add_cart/{product}/{email}")
    public ResponseEntity<String> deleteCartProduct( @PathVariable("product") String product,@PathVariable("email") String email);


    @DeleteMapping("/cart/{email}")
    public void deleteCartProducts(@PathVariable("email") String email);



}
