package com.example.token;

/**
 * Created by pweicai on 2/05/17.
 */
public interface ThirdPartyAccessTokenRequest extends AccessTokenRequest {
    String getAccessToken();
    String getRefreshToken();
}
