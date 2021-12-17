package com.example.cashcow_api.repositories;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.user.SummaryUserDTO;
import com.example.cashcow_api.models.EUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<EUser, Integer>, JpaSpecificationExecutor<EUser>{
    
    @Query(
        value = "SELECT * FROM users u "
            + "LEFT JOIN contacts c ON c.user_id = u.id "
            + "WHERE u.id = :userId "
               + "OR c.value = :contactValue",
        nativeQuery = true
    )
    Optional<EUser> findByIdOrContactValue(Integer userId, String contactValue);

    @Query(
        value = "SELECT * FROM users u "
            + "LEFT JOIN contacts c ON c.user_id = u.id "
            + "WHERE c.value = :contactValue",
        nativeQuery = true
    )
    Optional<EUser> findByContactValue(String contactValue);

    @Query(
        value = "SELECT * FROM users u "
            + "LEFT JOIN roles r ON r.id = u.role_id "
            + "WHERE u.role_id IN(:systemRoles)",
        nativeQuery = true
    )
    List<EUser> findSystemUsers(List<Integer> systemRoles);

    @Query(
        value = "SELECT new com.example.cashcow_api.dtos.user.SummaryUserDTO(COUNT(user) AS count, role.name) "
            + "FROM com.example.cashcow_api.models.EUser user "
            + "LEFT JOIN user.role role "
            + "GROUP BY role"
    )
    List<SummaryUserDTO> findUserCountByRole();
    
}
