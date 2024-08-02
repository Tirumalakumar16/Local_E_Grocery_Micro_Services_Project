package com.shop.ShopService.controller;

import com.shop.ShopService.dtos.RequestShopDtos;
import com.shop.ShopService.dtos.ResponseShopCustDto;
import com.shop.ShopService.dtos.ResponseShopDto;
import com.shop.ShopService.exceptions.ShopIsNotFoundException;
import com.shop.ShopService.exceptions.UserNotAutherizedException;
import com.shop.ShopService.exceptions.UserNotFound;
import com.shop.ShopService.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:5173/")
public class ShopController {
    private ShopService shopService;
    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/shop")
    public ResponseShopDto saveShop(@RequestBody RequestShopDtos requestShopDtos , @RequestHeader("LoggedInUser") String userName) throws UserNotFound {


            return shopService.saveShop(requestShopDtos,userName);

    }

    @GetMapping("/shops")
    public List<ResponseShopDto> getAll(@RequestHeader("LoggedInUser") String userName) throws UserNotAutherizedException, ShopIsNotFoundException {

            return shopService.getAll(userName);
    }

    @GetMapping("/shop/{city}")
    public List<ResponseShopCustDto> getByCity(@PathVariable("city") String city) throws ShopIsNotFoundException {
        return shopService.findByCity(city);
    }

}
