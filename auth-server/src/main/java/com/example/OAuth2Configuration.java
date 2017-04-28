package com.example;

import com.example.ctm.CtmOAuth2RequestFactory;
import com.example.ctm.CtmResourceOwnerPasswordTokenGranter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pweicai on 28/04/17.
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {
    private static final String SIGNING_KEY = "asdfasdfsfd";

    @Autowired
    private AuthorizationServerTokenServices tokenServices;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new EverestAuthenticationManager();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                //.requestFactory(oAuth2RequestFactory())
                .tokenGranter(tokenGranter());
                //.authenticationManager(authenticationManager());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer client) throws Exception {
        client.withClientDetails(clientDetailsService());
    }

    @Bean
    public TokenGranter tokenGranter() throws Exception {
        return new CtmResourceOwnerPasswordTokenGranter(authenticationManager(), tokenServices, clientDetailsService(), oAuth2RequestFactory());
    }

    @Bean
    public ClientDetailsService clientDetailsService() throws Exception {
        InMemoryClientDetailsServiceBuilder client = new InMemoryClientDetailsServiceBuilder();
        client.withClient("acme")
                .secret("acmesecret")
                .authorizedGrantTypes("password")
                .scopes("journey")
                .accessTokenValiditySeconds(600);
        return client.build();
    }
    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory() throws Exception {
        return new CtmOAuth2RequestFactory(clientDetailsService());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}
