package com.shop.ShopService.repository;

import com.shop.ShopService.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {

    @Query(value = "select * from grocery_shop.shop s where s.city=?1",nativeQuery = true)
    List<Shop> findByCity(String city);
    @Query(value = "select * from grocery_shop.shop s where s.email_id=?1",nativeQuery = true)
    Shop findByEmailId(String emailId);
}
