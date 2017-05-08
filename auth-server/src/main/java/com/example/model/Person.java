package com.example.model;

import java.util.Set;

/**
 * Created by pweicai on 8/05/17.
 */
public class Person {

    private String email;
    private String fullName;
    private Set<String> roles;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public void setRoles(final Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getRoles() {
        return this.roles;
    }
}