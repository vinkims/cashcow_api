package com.example.cashcow_api.services.auth;

import java.time.LocalDateTime;

import com.example.cashcow_api.models.EBlacklistToken;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.BlacklistTokenDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SBlacklist {
    
    @Autowired BlacklistTokenDAO blacklistTokenDAO;

    public Boolean checkExistsByToken(Integer tokenHash){
        return blacklistTokenDAO.existsByTokenHash(tokenHash);
    }

    /**
     * Create EBlacklistToken instance
     */
    public EBlacklistToken createBlacklistToken(String token, EUser user){

        EBlacklistToken blacklistToken = new EBlacklistToken();
        blacklistToken.setCreatedOn(LocalDateTime.now());
        blacklistToken.setTokenHash(token.hashCode());
        blacklistToken.setUser(user);
        save(blacklistToken);

        return blacklistToken;
    }

    public Integer getTokenHash(String token){
        return token.hashCode();
    }

    public void save(EBlacklistToken blacklistToken){
        blacklistTokenDAO.save(blacklistToken);
    }
}
