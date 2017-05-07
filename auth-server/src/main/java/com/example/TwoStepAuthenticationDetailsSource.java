package com.example;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by peter on 7/05/17.
 */
public class TwoStepAuthenticationDetailsSource implements
        AuthenticationDetailsSource<HttpServletRequest, TwoStepAuthenticationDetails> {

    @Override
    public TwoStepAuthenticationDetails buildDetails(HttpServletRequest httpServletRequest) {
        return new TwoStepAuthenticationDetails(httpServletRequest, null);
    }

    public TwoStepAuthenticationDetails buildDetails(HttpServletRequest httpServletRequest, OAuth2AccessToken token) {
        return new TwoStepAuthenticationDetails(httpServletRequest, token);
    }
}
