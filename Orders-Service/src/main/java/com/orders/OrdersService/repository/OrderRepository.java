package com.orders.OrdersService.repository;

import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails,Long> {

    @Query(value = "select * from grocery_order.order_details o where o.shop_name=?1",nativeQuery = true)
    List<OrderDetails> findByShopName(String shopName);
    @Query(value = "select * from grocery_order.order_details o where o.customer_email_id=?1",nativeQuery = true)
    List<OrderDetails> findByCustomerEmailId(String customerEmail);
}
