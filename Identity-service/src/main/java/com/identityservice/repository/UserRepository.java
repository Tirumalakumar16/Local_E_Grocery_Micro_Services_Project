package com.identityservice.repository;

import com.identityservice.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserCredentials,Long> {
    @Query("select u from UserCredentials u where u.username = ?1")
    Optional<UserCredentials> findByUsername(String username);
    @Query(value ="select  * from grocery_identity.user_credentials a where a.email_id=?1" ,nativeQuery = true)
    Optional<UserCredentials> findByEmailId(String email);
}
