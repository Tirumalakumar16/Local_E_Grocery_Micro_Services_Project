package com.products.ProductService.service;

import com.netflix.discovery.converters.Auto;
import com.products.ProductService.dtos.*;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductAndSellerEmail;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductName;
import com.products.ProductService.exceptions.ProductsNotAvailableWithShopName;
import com.products.ProductService.models.Product;
import com.products.ProductService.models.ProductStatus;
import com.products.ProductService.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<ResponseProductCustDto> getProductsByShopName(String shopName) throws ProductsNotAvailableWithShopName {
        List<Product> products = productRepository.findProductByShopNameLike(shopName);

        if(products.isEmpty()) {
            throw new ProductsNotAvailableWithShopName("Provided shopName doesn't exists with ");
        }

     return Arrays.asList(modelMapper.map(products, ResponseProductCustDto[].class));
    }




    @Override
    public ResponseProductCustDto getProduct(String productName) throws ProductsNotAvailableWithProductName {
        Product product = productRepository.findByProductName(productName);

        if(product == null) {
            throw new ProductsNotAvailableWithProductName("Provided product doesn't exists with ");
        }

        return modelMapper.map(product, ResponseProductCustDto.class);
    }


    @Override
    public ResponseProductDto getProductBySellerName(String productName, String emailId) throws ProductsNotAvailableWithShopName {
        Product product = productRepository.findByProductAndEmailId(productName,emailId);

        if(product == null) {
            throw new ProductsNotAvailableWithShopName("Provided product doesn't exists with ");
        }

        return modelMapper.map(product, ResponseProductDto.class);
    }

    @Override
    public ResponseProductDto updateProduct(RequestOwnerDto requestOwnerDto) throws ProductsNotAvailableWithProductAndSellerEmail {
         Product product = productRepository.findByProductAndEmailId(requestOwnerDto.getProductName(), requestOwnerDto.getEmailId());

        if(product == null) {
            throw new ProductsNotAvailableWithProductAndSellerEmail("Provided product doesn't exists with ");
        }

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
    public List<ResponseProductDto> getByEmail(String emailId) throws ProductsNotAvailableWithProductAndSellerEmail {
        List<Product> products = productRepository.findByEmailId(emailId);

        if(products.isEmpty()) {
            throw new ProductsNotAvailableWithProductAndSellerEmail("Products are not updated with your email and  products doesn't exists with "+emailId);
        }

        return Arrays.asList(modelMapper.map(products, ResponseProductDto[].class));
    }


    @Override
    public void updateByCustomer(RequestCustomerProductDto requestCustomerProductDto) {

         productRepository.updateQuantity(requestCustomerProductDto.getQuantity(),requestCustomerProductDto.getProductName(),requestCustomerProductDto.getShopName());
    }

    @Override
    public List<ResponseProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return Arrays.asList(modelMapper.map(products, ResponseProductDto[].class));
    }

    @Override
    public List<ResponseProductDto> getProducts(int pageNo) {

        int pageSize = 20;
        Pageable page = PageRequest.of(pageNo, pageSize);
        List<Product> products = productRepository.findAll(page).getContent();

        return Arrays.asList(modelMapper.map(products, ResponseProductDto[].class));

    }
}
