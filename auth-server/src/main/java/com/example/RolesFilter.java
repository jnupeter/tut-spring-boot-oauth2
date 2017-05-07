package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

/**
 * Created by pweicai on 5/05/17.
 */
public class RolesFilter extends OAuth2ClientAuthenticationProcessingFilter{

    private static final Logger LOGGER = Logger.getLogger(RolesFilter.class.getName());

    private TwoStepAuthenticationDetailsSource authenticationDetailsSource = new TwoStepAuthenticationDetailsSource();

    public RolesFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request,response,chain, authResult);
        OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authResult;  //both ways work
        //OAuth2Authentication oauth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, Object> userDetails = (LinkedHashMap<String, Object>)oauth2Authentication.getUserAuthentication().getDetails();
        LOGGER.info("=======principal:" + userDetails.get("email").toString());

        //oauth2Authentication.setDetails(authenticationDetailsSource.buildDetails(request));

        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("asdfasdfsfd");

        final JwtTokenStore jwtTokenStore = new JwtTokenStore(converter);

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(jwtTokenStore);
        tokenServices.setSupportRefreshToken(true);


        CTMTokenGranter tokenGranter = new CTMTokenGranter(tokenServices, oauth2Authentication);
        OAuth2AccessToken token = tokenGranter.grant("mytype", null);
        //LOGGER.info("+++++++++++" + token.getValue());
        ((OAuth2Authentication) authResult).setDetails(authenticationDetailsSource.buildDetails(request, token));
    }

}
