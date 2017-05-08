package com.example;

import com.example.authority.AuthorityService;
import com.example.ctm.CtmTokenRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by pweicai on 5/05/17.
 */
public class RolesFilter extends OAuth2ClientAuthenticationProcessingFilter{

    private static final Logger LOGGER = Logger.getLogger(RolesFilter.class.getName());

    private TwoStepAuthenticationDetailsSource authenticationDetailsSource = new TwoStepAuthenticationDetailsSource();
    private AuthorityService authorityService;

    public RolesFilter(String defaultFilterProcessesUrl, AuthorityService authorityService) {
        super(defaultFilterProcessesUrl);
        this.authorityService =  authorityService;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request,response,chain, authResult);
        OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authResult;  //both ways work
        //OAuth2Authentication oauth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, Object> userDetails = (LinkedHashMap<String, Object>)oauth2Authentication.getUserAuthentication().getDetails();
        String principal = userDetails.get("email").toString();
        LOGGER.info("issuing JWT token for principal:" + principal);

        //oauth2Authentication.setDetails(authenticationDetailsSource.buildDetails(request));

        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("asdfasdfsfd");

        final JwtTokenStore jwtTokenStore = new JwtTokenStore(converter);

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(jwtTokenStore);
        tokenServices.setSupportRefreshToken(true);

        InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        builder.withClient("mysys").authorities("MY_SYS")
                .resourceIds("journey")
                .accessTokenValiditySeconds(3600);
        ClientDetailsService clientDetailsService = null;

        try {
            clientDetailsService = builder.build();
            LOGGER.info("=========client detail service" + clientDetailsService);
            tokenServices.setClientDetailsService(clientDetailsService);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "failed to build client detail service");
            e.printStackTrace();
        }

        tokenServices.setTokenEnhancer(converter);  // critical
        CTMTokenGranter tokenGranter = new CTMTokenGranter(tokenServices, clientDetailsService);

        //building the request parameters
        Map<String,String> parameters = new HashMap<String, String>();
        parameters.put("username", principal);
        parameters.put("principal", principal);
        //parameters.put("grant_type", "ctm");

        //building scopes
        Set<String> scopes = new HashSet<>();
        scopes.add("journey");
        //building resourceId;

        CtmTokenRequest ctmTokenRequest = new CtmTokenRequest(parameters, "mysys",scopes, "ctm", "google");
        ctmTokenRequest.setAuthorities(authorityService.getAuthorities(principal));

        OAuth2AccessToken token = tokenGranter.grant("ctm", ctmTokenRequest);
        ((OAuth2Authentication) authResult).setDetails(authenticationDetailsSource.buildDetails(request, token));
        }
}
