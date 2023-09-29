package com.products.ProductService.service;

import com.products.ProductService.dtos.RequestOwnerDto;
import com.products.ProductService.dtos.RequestProductDto;
import com.products.ProductService.dtos.ResponseProductCustDto;
import com.products.ProductService.dtos.ResponseProductDto;

import java.util.List;

public interface ProductService {
    ResponseProductDto saveProduct(RequestProductDto requestProductDto);

    List<ResponseProductCustDto> getProductsByShopName(String shopName);

    ResponseProductCustDto getProduct(String productName);

    ResponseProductDto getProductBySellerName(String productName, String emailId);

    ResponseProductDto updateProduct(RequestOwnerDto requestOwnerDto);

    List<ResponseProductDto> getByEmail(String emailId);
}
