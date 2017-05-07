package com.example;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by peter on 7/05/17.
 */
public class TwoStepAuthenticationDetails implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(TwoStepAuthenticationDetails.class.getName());

    private OAuth2AccessToken ctmAccessToken;


    public TwoStepAuthenticationDetails(HttpServletRequest request, OAuth2AccessToken token) {
        this.setCtmAccessToken(token);
    }

    public OAuth2AccessToken getCtmAccessToken() {
        return this.ctmAccessToken;
    }

    public void setCtmAccessToken(final OAuth2AccessToken oAuth2AccessToken) {
        this.ctmAccessToken = oAuth2AccessToken;
    }

    @Override
    public String toString() {
        return "{\"CTM-Token\":\"abc\"}";
    }
}
