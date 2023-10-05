package com.products.ProductService.controller;

import com.products.ProductService.dtos.*;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductAndSellerEmail;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductName;
import com.products.ProductService.exceptions.ProductsNotAvailableWithShopName;
import com.products.ProductService.models.Product;
import com.products.ProductService.service.ProductService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<ResponseProductCustDto> getProductsByShopName(@PathVariable("shopName") String shopName) throws ProductsNotAvailableWithShopName {

        return productService.getProductsByShopName(shopName);
    }
     @GetMapping("/product/name/{productName}")
    public ResponseProductCustDto getProduct(@PathVariable("productName") String productName) throws ProductsNotAvailableWithProductName {

        return productService.getProduct(productName);
     }

    @GetMapping("/product/{productName}/{shopEmailId}")
    public ResponseProductDto getProductBySellerName(@PathVariable("productName") String productName,@PathVariable("shopEmailId") String emailId) throws ProductsNotAvailableWithShopName {

        return productService.getProductBySellerName(productName,emailId);
    }

    @PutMapping("/product")
    public ResponseProductDto updateProduct(@RequestBody RequestOwnerDto requestOwnerDto) throws ProductsNotAvailableWithProductAndSellerEmail {

        return productService.updateProduct(requestOwnerDto);
    }

    @GetMapping("/product/email/{emailId}")
    public List<ResponseProductDto> getByEmail(@PathVariable("emailId") String emailId) throws ProductsNotAvailableWithProductAndSellerEmail {

        return productService.getByEmail(emailId);
    }

    @PutMapping("/product/customer")
    public void updateByCustomer(@RequestBody RequestCustomerProductDto requestCustomerProductDto) {

         productService.updateByCustomer(requestCustomerProductDto);
    }




}
