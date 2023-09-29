package com.customer.CustomerService.repository;

import com.customer.CustomerService.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "select * from grocery_customer.customer c where c.email_id=?1",nativeQuery = true)
    Customer findByEmailId(String emailId);
}
