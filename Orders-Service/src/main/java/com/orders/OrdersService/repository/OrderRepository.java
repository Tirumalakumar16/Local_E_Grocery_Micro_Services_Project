package com.orders.OrdersService.repository;

import com.orders.OrdersService.dtos.ResponseOrderShopDto;
import com.orders.OrdersService.dtos.ResponseOrdersShopTotalDto;
import com.orders.OrdersService.dtos.customer.ResponseOrderCustomerDateDto;
import com.orders.OrdersService.dtos.customer.ResponseOrdersCustomerTotalDto;
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

    @Query(value = "select new com.orders.OrdersService.dtos.ResponseOrderShopDto(a.createdAt,a.customerName,a.customerEmailId,a.houseNumber,a.shopName,sum(a.price*a.quantity)) from OrderDetails As a" +
            " where a.shopName=?1 group by a.customerEmailId,a.customerName,a.houseNumber,a.createdAt,a.shopName")
    List<ResponseOrderShopDto> findTotalByCustomerWise(String shopName);

    @Query("select  new com.orders.OrdersService.dtos.ResponseOrdersShopTotalDto(a.customerName,a.customerEmailId,a.shopName,SUM(a.price*a.quantity))from OrderDetails AS a where a.shopName=?1 group by a.customerEmailId,a.customerName,a.shopName")
    List<ResponseOrdersShopTotalDto> findTotalAmountOfCustomersPerShop(String shopName);

    @Query("select  new com.orders.OrdersService.dtos.customer.ResponseOrdersCustomerTotalDto(a.customerName,a.customerEmailId,a.shopName,SUM(a.price*a.quantity))from OrderDetails AS a where a.customerEmailId=?1 group by a.customerEmailId,a.customerName,a.shopName")
    List<ResponseOrdersCustomerTotalDto> findTotalAmountForCustomerPerShop(String email);

    @Query(value = "select new com.orders.OrdersService.dtos.customer.ResponseOrderCustomerDateDto(a.createdAt,a.customerName,a.customerEmailId,a.houseNumber,a.shopName,sum(a.price*a.quantity),a.transactionId) from OrderDetails As a" +
            " where a.customerEmailId=?1 group by a.customerEmailId,a.customerName,a.houseNumber,a.createdAt,a.shopName,a.transactionId")
    List<ResponseOrderCustomerDateDto> findAllOrdersOnDateForCustomer(String email);
}
