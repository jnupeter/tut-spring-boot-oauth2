package com.example.token;

/**
 * Created by pweicai on 2/05/17.
 */
public interface UernamePasswordAccessTokenRequest extends AccessTokenRequest {
    String getUsername();
    String getPassword();
}
