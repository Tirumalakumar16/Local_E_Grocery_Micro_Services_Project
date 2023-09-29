package com.shop.ShopService.feignclients;

import com.products.ProductService.dtos.RequestOwnerDto;
import com.products.ProductService.dtos.RequestProductDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductAndSellerEmail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Product-Service",path = "/www.localGrocery.com/product/api")
public interface ProductFeignClient {

    @PostMapping("/product")
    public ResponseProductDto saveProduct(@RequestBody RequestProductDto requestProductDto);

    @GetMapping("/product/email/{emailId}")
    public List<ResponseProductDto> getByEmail(@PathVariable("emailId") String emailId) throws ProductsNotAvailableWithProductAndSellerEmail;

    @PutMapping("/product")
    public ResponseProductDto updateProduct(@RequestBody RequestOwnerDto requestOwnerDto) throws ProductsNotAvailableWithProductAndSellerEmail;
}
