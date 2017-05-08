package com.example.authority;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * Created by pweicai on 8/05/17.
 */
public interface AuthorityService {
    Set<? extends GrantedAuthority> getAuthorities(String principal) throws AuthorityException;
}
