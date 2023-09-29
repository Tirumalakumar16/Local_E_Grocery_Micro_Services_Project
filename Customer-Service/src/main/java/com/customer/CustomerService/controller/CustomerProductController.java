package com.customer.CustomerService.controller;

import com.customer.CustomerService.dtos.RequestProductDto;
import com.customer.CustomerService.service.CustomerService;
import com.products.ProductService.dtos.ResponseProductCustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerProductController {


    private CustomerService customerService;
    @Autowired
    public CustomerProductController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/customer/product")
    public List<ResponseProductCustDto> getProductByShopName(@RequestBody RequestProductDto requestProductDto) {

        return customerService.getByShopName(requestProductDto.getShopName());
    }



}
