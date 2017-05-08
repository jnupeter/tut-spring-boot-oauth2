package com.example.ctm;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.logging.Logger;

/**
 * Created by pweicai on 8/05/17.
 */
public class CTMTokenEnhancer implements TokenEnhancer {
    private static final Logger LOGGER = Logger.getLogger(CTMTokenEnhancer.class.getName());

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        LOGGER.info("accessToken is: " + accessToken.getClass().getSimpleName());
        DefaultOAuth2AccessToken aToken = (DefaultOAuth2AccessToken) accessToken;
        aToken.getAdditionalInformation().put("additioninfo", "myadditioninfo");
        return aToken;
    }
}
