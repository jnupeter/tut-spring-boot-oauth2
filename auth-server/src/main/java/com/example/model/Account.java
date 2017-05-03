package com.example.model;

/**
 * Created by pweicai on 2/05/17.
 */
public class Account {
    private Integer accountId;
    private boolean anonymous;
    private String email;
    private String firstName;
    private String lastName;
    private String idp;
    private String idpPrincipal;

    public Integer getAccountId() {
        return this.accountId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setAccountId(final Integer accountId) {
        this.accountId = accountId;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
