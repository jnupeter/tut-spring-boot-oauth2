package com.example;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by pweicai on 5/05/17.
 */
public class RolesFilter extends OAuth2ClientAuthenticationProcessingFilter{

    private static final Logger LOGGER = Logger.getLogger(RolesFilter.class.getName());


    public RolesFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request,response,chain, authResult);
        LOGGER.info("=======principal:" + authResult.getClass().getSimpleName());
        authResult.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));
        LOGGER.info("=============authorization size====" + authResult.getAuthorities().size());
        LOGGER.info("============================successfully authentication===");
    }

}
