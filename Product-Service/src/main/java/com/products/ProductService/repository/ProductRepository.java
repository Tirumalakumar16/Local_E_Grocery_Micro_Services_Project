package com.products.ProductService.repository;

import com.products.ProductService.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "select * from grocery_product.product p where p.shop_name=?1",nativeQuery = true)
    List<Product> findByShopName(String shopName);
    @Query(value = "select * from grocery_product.product p where p.product_name=?1",nativeQuery = true)
    Product findByProductName(String productName);

    @Query(value = "select * from grocery_product.product p where p.product_name=?1 and p.email_id=?2",nativeQuery = true)
    Product findByProductAndEmailId(String productName, String emailId);
    @Query(value = "select * from grocery_product.product p where p.email_id=?1",nativeQuery = true)
    List<Product> findByEmailId(String emailId);
}
