package com.example;

import com.example.ctm.CtmUsernamePasswordAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;

/**
 * Created by pweicai on 28/04/17.
 */
public class EverestAuthenticationManager implements AuthenticationManager {

    private static final Logger LOG = LoggerFactory.getLogger(EverestAuthenticationManager.class);

    @Override
    public Authentication authenticate(Authentication token) throws AuthenticationException {
        //CtmUsernamePasswordAuthenticationToken token = (CtmUsernamePasswordAuthenticationToken)authentication;
        LOG.info("=============" + token.getClass().getSimpleName());
        LOG.info("username:" + token.getName());
        LOG.info("credential:" + token.getCredentials().toString());
        //LOG.info("idprovider:" + token.getIdProvider());

        ArrayList<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("POWERFUL_USER"));
        roles.add(new SimpleGrantedAuthority("ANOTHER_USER"));
        return new UsernamePasswordAuthenticationToken("bob", null, roles);
        /*if(authentication.isAuthenticated()) {
            LOG.info("username:" + authentication.getPrincipal().toString());
            return authentication;
        }*/

    }
}
