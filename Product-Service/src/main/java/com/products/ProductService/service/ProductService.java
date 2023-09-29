package com.products.ProductService.service;

import com.products.ProductService.dtos.RequestOwnerDto;
import com.products.ProductService.dtos.RequestProductDto;
import com.products.ProductService.dtos.ResponseProductCustDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductAndSellerEmail;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductName;
import com.products.ProductService.exceptions.ProductsNotAvailableWithShopName;

import java.util.List;

public interface ProductService {
    ResponseProductDto saveProduct(RequestProductDto requestProductDto);

    List<ResponseProductCustDto> getProductsByShopName(String shopName) throws ProductsNotAvailableWithShopName;

    ResponseProductCustDto getProduct(String productName) throws ProductsNotAvailableWithProductName;

    ResponseProductDto getProductBySellerName(String productName, String emailId) throws ProductsNotAvailableWithShopName;

    ResponseProductDto updateProduct(RequestOwnerDto requestOwnerDto) throws  ProductsNotAvailableWithProductAndSellerEmail;

    List<ResponseProductDto> getByEmail(String emailId) throws ProductsNotAvailableWithProductAndSellerEmail;
}
