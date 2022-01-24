package com.example.cashcow_api.services.auth;

import com.example.cashcow_api.models.EBlacklistToken;
import com.example.cashcow_api.models.EUser;

public interface IBlacklist {
    
    Boolean checkExistsByToken(Integer tokenHash);

    public EBlacklistToken createBlacklistToken(String token, EUser user);

    Integer getTokenHash(String token);

    void save(EBlacklistToken blacklistToken);
}
