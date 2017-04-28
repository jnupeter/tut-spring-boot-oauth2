package com.example.ctm;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by pweicai on 28/04/17.
 */
public class CtmUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String idProvider;

    public CtmUsernamePasswordAuthenticationToken(Object principal, Object credentials, String idProvider) {
        super(principal, credentials);
        this.idProvider = idProvider;
    }

    public CtmUsernamePasswordAuthenticationToken(Object principal,
                                                  Object credentials,
                                                  String idProvider,
                                                  Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.idProvider = idProvider;
    }

    public String getIdProvider() {
        return this.idProvider;
    }
}
