package com.customer.CustomerService.feignclients;

import com.products.ProductService.dtos.ResponseProductCustDto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductName;
import com.products.ProductService.exceptions.ProductsNotAvailableWithShopName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Product-Service" ,path = "/www.localGrocery.com/product/api")
public interface ProductFeignClient {

    @GetMapping("/product/shop/{shopName}")
    public List<ResponseProductCustDto> getProductsByShopName(@PathVariable("shopName") String shopName) throws ProductsNotAvailableWithShopName;

    @GetMapping("/product/name/{productName}")
    public ResponseProductCustDto getProduct(@PathVariable("productName") String productName) throws ProductsNotAvailableWithProductName;




}
