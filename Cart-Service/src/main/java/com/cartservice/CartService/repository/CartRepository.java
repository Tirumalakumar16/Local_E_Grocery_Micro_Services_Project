package com.cartservice.CartService.repository;

import com.cartservice.CartService.models.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query(value = "select * from grocery_cart.cart c where c.email_id=?1",nativeQuery = true)
    List<Cart> findByEmailId(String email);
    @Query(value = "select * from grocery_cart.cart c where c.email_id=?1 and c.product_name=?2",nativeQuery = true)
    Cart findByEmailIdAndProductName(String emailId, String productName);
    @Query(value = "delete from grocery_cart.cart c where c.email_id=?1 and c.product_name=?2",nativeQuery = true)
    void deleteByEmailAndProductName(String email, String product);
    @Modifying
    @Transactional
    @Query(value = "delete  from grocery_cart.cart c where c.email_id=?1",nativeQuery = true)
    void deleteByEmailId(String email);
}
