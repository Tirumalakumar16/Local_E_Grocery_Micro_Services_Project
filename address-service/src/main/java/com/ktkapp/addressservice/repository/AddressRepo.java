package com.ktkapp.addressservice.repository;

import com.ktkapp.addressservice.models.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {

    @Query(value = "select * from grocery_address.address a where a.email_id=?1",nativeQuery = true)
    List<Address> findAllByEmailId(String email);
    @Modifying
    @Transactional
    @Query(value = "delete from grocery_address.address a where a.zip=?1 and a.email_id=?2 and a.house_number=?3",nativeQuery = true)
     void deleteByZipAndEmailId(String deleteAddress,String email,String houseNumber);
    @Query(value = "select * from grocery_address.address a where a.zip =?1 and a.email_id=?2",nativeQuery = true)
    Address findByZipAndEmailId(String zip, String emailId);

    @Query(value = "select * from grocery_address.address a where a.email_id=?1 and a.house_number =?2 and a.zip=?3",nativeQuery = true)
    Address findByEmailIdAndHouseNumberAndZip(String emailId,String houseNumber,String zip);
    @Query(value = "select * from grocery_address.address a where a.house_number=?1",nativeQuery = true)
    Address findByHouseNumber(String houseNumber);
}
