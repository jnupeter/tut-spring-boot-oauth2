package com.example.api;

import com.example.model.Account;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pweicai on 2/05/17.
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable String id) {
        final Account account = new Account();
        account.setAccountId(id);
        account.setEmail("jnupeter@gmail.com");
        return account;
    }

    @PostMapping("/tokens")
    public OAuth2AccessToken issueToken(final CTMAccessTokenRequest tokenRequest) {
        return tokenService.issueToken(tokenRequest);
    }
}
