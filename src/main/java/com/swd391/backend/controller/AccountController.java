package com.swd391.backend.controller;

import com.swd391.backend.entity.User;
import com.swd391.backend.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:3000, https://api.ani-testlab.edu.vn")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody User user) {
        ResponseEntity<?> response = accountService.register(user);
        return response;
    }
}
