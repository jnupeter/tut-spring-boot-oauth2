package com.example.token;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.util.*;

/**
 * Created by peter on 7/05/17.
 */
public class FakeOAuth2AccessToken implements OAuth2AccessToken {
    @Override
    public Map<String, Object> getAdditionalInformation() {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("myname","Peter");
        return additionalInfo;
    }

    @Override
    public Set<String> getScope() {
        Set<String> scope = new HashSet<String>();
        scope.add("journey");
        return scope;
    }

    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return () -> "this is a refresh token";
    }

    @Override
    public String getTokenType() {
        return "bearer";
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public Date getExpiration() {
        return new Date();
    }

    @Override
    public int getExpiresIn() {
        return 3600;
    }

    @Override
    public String getValue() {
        return "This is access token.";
    }
}
