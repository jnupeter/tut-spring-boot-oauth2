package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * Created by pweicai on 8/05/17.
 */
@Configuration
@EnableConfigurationProperties
public class LdapConfiguration {
    @Bean
    @ConfigurationProperties(prefix="ctm.ldap.contextSource")
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }
}