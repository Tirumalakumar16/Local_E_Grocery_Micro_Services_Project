package com.payment.PaymentService.repository;

import com.payment.PaymentService.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Query(value = "select * from grocery_payment.payment p where p.customer_email_id=?1",nativeQuery = true)
    List<Payment> finByEmailId(String email);
}
