package com.example.cashcow_api.repositories;

import java.util.List;

import com.example.cashcow_api.models.ECow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CowDAO extends JpaRepository<ECow, Integer>, JpaSpecificationExecutor<ECow> {
    
    Boolean existsByName(String cowName);

    @Query(
        value = "SELECT * from cows c "
            + "LEFT JOIN cow_profiles cp ON cp.cow_id = c.id "
            + "WHERE cp.gender = :gender",
        nativeQuery = true
    )
    List<ECow> findCowsByGender(String gender);
}
