package com.customer.CustomerService.controller;

import com.customer.CustomerService.dtos.RequestProductDto;
import com.customer.CustomerService.service.CustomerService;
import com.products.ProductService.dtos.ResponseProductCustDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithShopName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:5173/")
public class CustomerProductController {


    private CustomerService customerService;
    @Autowired
    public CustomerProductController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/customer/product")
    public List<ResponseProductCustDto> getProductByShopName(@RequestBody RequestProductDto requestProductDto) throws ProductsNotAvailableWithShopName {

        return customerService.getByShopName(requestProductDto.getShopName());
    }

    @GetMapping("/customer/products")
    public List<ResponseProductDto> getAllProducts(){
        return customerService.getAllProducts();
    }

    @GetMapping("/customer/product/{pageNo}")
    public List<ResponseProductDto> getProducts(@PathVariable("pageNo") int pageNo){
        return customerService.getProducts(pageNo);
    }

}
