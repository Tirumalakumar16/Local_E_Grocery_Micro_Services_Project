package com.products.ProductService.service;

import com.products.ProductService.dtos.*;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductAndSellerEmail;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductName;
import com.products.ProductService.exceptions.ProductsNotAvailableWithShopName;
import com.products.ProductService.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ResponseProductDto saveProduct(RequestProductDto requestProductDto);

    List<ResponseProductCustDto> getProductsByShopName(String shopName) throws ProductsNotAvailableWithShopName;

    ResponseProductCustDto getProduct(String productName) throws ProductsNotAvailableWithProductName;

    ResponseProductDto getProductBySellerName(String productName, String emailId) throws ProductsNotAvailableWithShopName;

    ResponseProductDto updateProduct(RequestOwnerDto requestOwnerDto) throws  ProductsNotAvailableWithProductAndSellerEmail;

    List<ResponseProductDto> getByEmail(String emailId) throws ProductsNotAvailableWithProductAndSellerEmail;

    void updateByCustomer(RequestCustomerProductDto requestCustomerProductDto);
}
