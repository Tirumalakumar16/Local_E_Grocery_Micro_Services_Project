package com.shop.ShopService.controller;

import com.products.ProductService.dtos.RequestOwnerDto;
import com.products.ProductService.dtos.RequestProductDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductAndSellerEmail;
import com.shop.ShopService.dtos.product.RequestProductShopDto;
import com.shop.ShopService.exceptions.UserNotAutherizedException;
import com.shop.ShopService.exceptions.UserNotFound;
import com.shop.ShopService.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OwnerController {

    private ShopService shopService;
    @Autowired
    public OwnerController(ShopService shopService) {
        this.shopService = shopService;
    }
    @PostMapping("/product")
    public ResponseProductDto saveProduct(@RequestBody RequestProductShopDto requestProductDto, @RequestHeader("LoggedInUser") String userName) {

        try {
            return shopService.saveProduct(requestProductDto,userName);
        } catch (UserNotFound e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/products")
    public List<ResponseProductDto> getAllProducts(@RequestHeader("LoggedInUser") String userName) throws UserNotFound, ProductsNotAvailableWithProductAndSellerEmail {

            return shopService.getAllProducts(userName);

    }
    @PutMapping("/product")
    public ResponseProductDto updateProduct(@RequestHeader("LoggedInUser") String userName, @RequestBody RequestOwnerDto requestOwnerDto) throws UserNotFound, UserNotAutherizedException, ProductsNotAvailableWithProductAndSellerEmail {

        return shopService.updateProduct(userName,requestOwnerDto);
    }
}
