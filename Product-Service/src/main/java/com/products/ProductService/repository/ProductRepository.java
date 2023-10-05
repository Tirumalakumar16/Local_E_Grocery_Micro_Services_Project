package com.products.ProductService.repository;

import com.products.ProductService.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    @Modifying
    @Transactional
    @Query(value = "update grocery_product.product p set p.quantity =?1 where p.product_name=?2 and p.shop_name=?3",nativeQuery = true)
    void updateQuantity(int quantity, String productName, String shopName);
}
