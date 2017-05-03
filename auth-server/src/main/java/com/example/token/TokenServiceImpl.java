package com.example.token;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * Created by pweicai on 3/05/17.
 */
@Component
public class TokenServiceImpl implements TokenService {
    @Override
    public OAuth2AccessToken issueToken(AccessTokenRequest tokenRequest) throws TokenException {
        return null;
    }
}
