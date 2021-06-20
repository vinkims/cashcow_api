package com.example.cashcow_api.repositories;

import com.example.cashcow_api.models.EBlacklistToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistTokenDAO extends JpaRepository<EBlacklistToken, Integer> {
    
    Boolean existsByTokenHash(Integer tokenHash);
}
