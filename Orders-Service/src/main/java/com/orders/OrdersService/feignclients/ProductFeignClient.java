package com.orders.OrdersService.feignclients;

import com.products.ProductService.dtos.RequestCustomerProductDto;
import com.products.ProductService.dtos.ResponseProductCustDto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductName;
import com.products.ProductService.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "Product-Service",path = "/www.localGrocery.com/product/api")
public interface ProductFeignClient {


    @GetMapping("/product/name/{productName}")
    public ResponseProductCustDto getProduct(@PathVariable("productName") String productName) throws ProductsNotAvailableWithProductName;

    @PutMapping("/product/customer")
    public void updateByCustomer(@RequestBody RequestCustomerProductDto requestCustomerProductDto);
}
