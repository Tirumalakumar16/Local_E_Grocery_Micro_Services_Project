package com.products.ProductService.controller;

import com.products.ProductService.dtos.RequestOwnerDto;
import com.products.ProductService.dtos.RequestProductDto;
import com.products.ProductService.dtos.ResponseProductCustDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.products.ProductService.service.ProductService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/product")
    public ResponseProductDto saveProduct(@RequestBody RequestProductDto requestProductDto) {

        return productService.saveProduct(requestProductDto);
    }

    @GetMapping("/product/shop/{shopName}")
    public List<ResponseProductCustDto> getProductsByShopName(@PathVariable("shopName") String shopName) {

        return productService.getProductsByShopName(shopName);
    }
     @GetMapping("/product/name/{productName}")
    public ResponseProductCustDto getProduct(@PathVariable("productName") String productName) {

        return productService.getProduct(productName);
     }

    @GetMapping("/product/{productName}/{shopEmailId}")
    public ResponseProductDto getProductBySellerName(@PathVariable("productName") String productName,@PathVariable("shopEmailId") String emailId) {

        return productService.getProductBySellerName(productName,emailId);
    }

    @PutMapping("/product")
    public ResponseProductDto updateProduct(@RequestBody RequestOwnerDto requestOwnerDto) {

        return productService.updateProduct(requestOwnerDto);
    }

    @GetMapping("/product/email/{emailId}")
    public List<ResponseProductDto> getByEmail(@PathVariable("emailId") String emailId) {

        return productService.getByEmail(emailId);
    }




}
