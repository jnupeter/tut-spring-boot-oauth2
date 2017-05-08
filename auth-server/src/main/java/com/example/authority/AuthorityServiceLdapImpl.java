package com.example.authority;

import com.example.model.Person;
import com.example.model.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by pweicai on 8/05/17.
 */
@Component
public class AuthorityServiceLdapImpl implements AuthorityService {
    private static final Logger LOGGER = Logger.getLogger(AuthorityServiceLdapImpl.class.getName());

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public Set<? extends GrantedAuthority> getAuthorities(String principal) throws AuthorityException {
        LOGGER.info("Getting roles for " + principal);
        LdapQuery query = LdapQueryBuilder.query()
                .base("OU=AIH Users")
                .searchScope(SearchScope.SUBTREE)
                .attributes("distinguishedName", "mail", "cn", "displayname", "memberOf")
                .where("mail")
                .is(principal);

        List<Person> persons = ldapTemplate.search(query, new PersonMapper());

        LOGGER.info("how many persons found: " + persons.size());
        if (persons.isEmpty()) {
            return Collections.EMPTY_SET;
        } else {
            Set<SimpleGrantedAuthority> roles = new HashSet<>();
            for(String role : persons.get(0).getRoles()) {
                roles.add(new SimpleGrantedAuthority(role));
            }
            return roles;
        }
    }
}
