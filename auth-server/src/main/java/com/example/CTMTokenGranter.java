package com.example;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.io.Serializable;
import java.util.*;

/**
 * Created by peter on 7/05/17.
 */
public class CTMTokenGranter implements TokenGranter {

    private AuthorizationServerTokenServices tokenServices;
    private OAuth2Authentication oAuth2Authentication;

    public CTMTokenGranter(AuthorizationServerTokenServices tokenServices, OAuth2Authentication authentication) {
        this.tokenServices = tokenServices;
        this.oAuth2Authentication = authentication;
    }

    @Override
    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
        return tokenServices.createAccessToken(getOAutheAuthentication());
    }

    private OAuth2Authentication getOAutheAuthentication() {
        final OAuth2Authentication result;

        Map<String, String> requestParameters = new HashMap<>();
        String clientId = "mysys";
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("myrole"));

        boolean approved = true;

        Set<String> scope = new HashSet<>();
        scope.add("journey");

        Set<String> resourceIds = new HashSet<>();
        scope.add("journey");

        String redirectUri = "http://localhost:9090/";
        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("mytype");

        Map<String, Serializable > extensionProperties = new HashMap<>();
        OAuth2Request request = new OAuth2Request(requestParameters,
                                                  clientId,
                                                  authorities,
                                                  approved,
                                                  scope,
                resourceIds,
                redirectUri,
                responseTypes,
                extensionProperties);

        //============================
        ArrayList<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("POWERFUL_USER"));
        roles.add(new SimpleGrantedAuthority("ANOTHER_USER"));
        Authentication userAuthentication = new UsernamePasswordAuthenticationToken("jnupeter@gmail.com", null, roles);

        result = new OAuth2Authentication(request, userAuthentication);
        result.setAuthenticated(true);

        return result;
    }
}
