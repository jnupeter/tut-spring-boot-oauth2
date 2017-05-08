package com.example;

import com.example.ctm.CtmTokenRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by peter on 7/05/17.
 */
public class CTMTokenGranter{

    private static final Logger LOGGER = Logger.getLogger(CTMTokenGranter.class.getName());
    private AuthorizationServerTokenServices tokenServices;
    private ClientDetailsService clientDetailsService;

    public CTMTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService) {
        this.tokenServices = tokenServices;
        this.clientDetailsService = clientDetailsService;
    }

    public OAuth2AccessToken grant(String grantType, CtmTokenRequest ctmTokenRequest) {
        return tokenServices.createAccessToken(getOAuth2Authentication(ctmTokenRequest));
    }

    private OAuth2Authentication getOAuth2Authentication(CtmTokenRequest ctmTokenRequest) {
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(ctmTokenRequest.getClientId());
        OAuth2Request request = ctmTokenRequest.createOAuth2Request(clientDetails);

        String username = ctmTokenRequest.getRequestParameters().get("username");
        Set<? extends GrantedAuthority> roles = ctmTokenRequest.getAuthorities();

        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(username, null, roles);
        return new OAuth2Authentication(request, userAuthentication);
    }
}
