package com.example.token;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Created by pweicai on 2/05/17.
 */
public interface TokenService {
    OAuth2AccessToken issueToken(AccessTokenRequest tokenRequest);
}
