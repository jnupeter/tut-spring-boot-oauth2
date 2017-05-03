package com.example.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pweicai on 3/05/17.
 */
@RestController
public class LoginController {

    @GetMapping("/login/github")
    public void get() {
        System.out.println("================get==============");
    }

    @PostMapping("/login/github")
    public void post() {
        System.out.println("===============post===============");
    }
}
