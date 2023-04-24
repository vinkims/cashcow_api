package com.example.cashcow_api.repositories;

import java.util.Optional;

import com.example.cashcow_api.models.EContact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ContactDAO extends JpaRepository<EContact, Integer>{
    
    Boolean existsByValue(String contactValue);

    @Query(
        value = "SELECT * FROM contacts c "
            + "WHERE c.user_id = :userId "
            + "AND c.contact_type_id = :contactTypeId",
        nativeQuery = true
    )
    Optional<EContact> findByUserIdAndContactTypeId(Integer userId, Integer contactTypeId);

    EContact findByValue(String contactValue);

    @Transactional
    @Modifying
    @Query(
        value = "UPDATE contacts SET "
            + "value = :contactValue "
            + "WHERE user_id = :userId "
                + "AND contact_type_id = :contactTypeId",
        nativeQuery = true
    )
    void updateContact(String contactValue, Integer userId, Integer contactTypeId);
}
