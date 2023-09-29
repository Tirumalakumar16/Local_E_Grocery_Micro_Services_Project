package com.products.ProductService.service;

import com.netflix.discovery.converters.Auto;
import com.products.ProductService.dtos.RequestOwnerDto;
import com.products.ProductService.dtos.RequestProductDto;
import com.products.ProductService.dtos.ResponseProductCustDto;
import com.products.ProductService.dtos.ResponseProductDto;
import com.products.ProductService.models.Product;
import com.products.ProductService.models.ProductStatus;
import com.products.ProductService.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    private ModelMapper modelMapper;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseProductDto saveProduct(RequestProductDto requestProductDto) {
        Product product = new Product();
        product.setProductName(requestProductDto.getProductName());
        product.setPrice(requestProductDto.getPrice());
        product.setAvailable(ProductStatus.Avilable);
        product.setQuantity(requestProductDto.getQuantity());
        product.setEmailId(requestProductDto.getEmailId());
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        product.setShopName(requestProductDto.getShopName());
        product.setCategory(requestProductDto.getCategory());

        Product product1 = productRepository.save(product);

        return modelMapper.map(product1, ResponseProductDto.class);
    }


    @Override
    public List<ResponseProductCustDto> getProductsByShopName(String shopName) {
        List<Product> products = productRepository.findByShopName(shopName);

     return Arrays.asList(modelMapper.map(products, ResponseProductCustDto[].class));
    }


    @Override
    public ResponseProductCustDto getProduct(String productName) {
        Product product = productRepository.findByProductName(productName);

        return modelMapper.map(product, ResponseProductCustDto.class);
    }


    @Override
    public ResponseProductDto getProductBySellerName(String productName, String emailId) {
        Product product = productRepository.findByProductAndEmailId(productName,emailId);

        return modelMapper.map(product, ResponseProductDto.class);
    }

    @Override
    public ResponseProductDto updateProduct(RequestOwnerDto requestOwnerDto) {
         Product product = productRepository.findByProductAndEmailId(requestOwnerDto.getProductName(), requestOwnerDto.getEmailId());

         if(requestOwnerDto.getProductName() != null) {
             product.setProductName(requestOwnerDto.getProductName());
         }
         double priceOwner = requestOwnerDto.getPrice();
         if(priceOwner != 0) {
             product.setPrice(requestOwnerDto.getPrice());
         }
         int quantityOwner = requestOwnerDto.getQuantity();
         if(quantityOwner != 0) {
             product.setQuantity(requestOwnerDto.getQuantity());
         }

         Product product1 = productRepository.save(product);

        return modelMapper.map(product1, ResponseProductDto.class);
    }


    @Override
    public List<ResponseProductDto> getByEmail(String emailId) {
        List<Product> products = productRepository.findByEmailId(emailId);

        return Arrays.asList(modelMapper.map(products, ResponseProductDto[].class));
    }
}
