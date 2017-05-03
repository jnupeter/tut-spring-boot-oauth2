package com.example.api;

import com.example.model.Account;
import com.example.token.AccessTokenRequest;
import com.example.token.TokenException;
import com.example.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pweicai on 2/05/17.
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable String id) {
        final Account account = new Account();
        account.setAccountId(Integer.parseInt(id));
        account.setEmail("jnupeter@gmail.com");
        return account;
    }

    @PostMapping("/tokens")
    public OAuth2AccessToken issueToken(final AccessTokenRequest tokenRequest) {
        try {
            return tokenService.issueToken(tokenRequest);
        } catch (TokenException e) {
            e.printStackTrace();
        }
        return null;
    }
}
