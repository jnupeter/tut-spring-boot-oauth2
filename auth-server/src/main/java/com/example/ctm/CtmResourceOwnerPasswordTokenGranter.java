package com.example.ctm;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by pweicai on 28/04/17.
 */
public class CtmResourceOwnerPasswordTokenGranter extends ResourceOwnerPasswordTokenGranter {

    private AuthenticationManager authenticationManager;

    public CtmResourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory);
        this.authenticationManager = authenticationManager;
    }

    protected CtmResourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        CtmTokenRequest ctmTokenRequest = (CtmTokenRequest) tokenRequest;
        Map<String, String> parameters = new LinkedHashMap<String, String>(ctmTokenRequest.getRequestParameters());
        String username = parameters.get("username");
        String password = parameters.get("password");
        // Protect from downstream leaks of password
        parameters.remove("password");

        //Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
        Authentication userAuth = new CtmUsernamePasswordAuthenticationToken(username, password, ctmTokenRequest.getIdProvider());
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        }
        catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        }
        catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
