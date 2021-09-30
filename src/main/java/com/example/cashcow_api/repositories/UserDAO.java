package com.example.cashcow_api.repositories;

import java.util.Optional;

import com.example.cashcow_api.models.EUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<EUser, Integer>, JpaSpecificationExecutor<EUser>{
    
    @Query(
        value = "SELECT * FROM users u "
            + "LEFT JOIN contacts c ON c.user_id = u.id "
            + "WHERE c.value = :contactValue",
        nativeQuery = true
    )
    Optional<EUser> findByContactValue(String contactValue);
}
