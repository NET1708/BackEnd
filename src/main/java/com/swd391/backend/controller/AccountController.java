package com.swd391.backend.controller;

import com.swd391.backend.entity.User;
import com.swd391.backend.security.JwtResponse;
import com.swd391.backend.security.LoginRequest;
import com.swd391.backend.service.AccountService;
import com.swd391.backend.service.JwtService;
import com.swd391.backend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:3000, https://api.ani-testlab.edu.vn")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody User user) {
        ResponseEntity<?> response = accountService.register(user);
        return response;
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam String email, @RequestParam String code) {
        ResponseEntity<?> response = accountService.activateAccount(email, code);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            if(authentication.isAuthenticated()) {
                final String jwt = jwtService.generateToken(loginRequest.getUsername());
                return ResponseEntity.ok(new JwtResponse(jwt));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Sai tên đăng nhập hoặc mật khẩu!");
        }
        return ResponseEntity.badRequest().body("Xác thực thất bại!");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        ResponseEntity<?> response = accountService.forgotPassword(email);
        return response;
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String email, @RequestParam String newPassword, @RequestParam String confirmPassword  ) {
        ResponseEntity<?> response = accountService.changePassword(email, newPassword, confirmPassword);
        return response;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserInfo(@RequestHeader String token) {
        return ResponseEntity.ok(userService.findByUsername(jwtService.extractUsername(token)));
    }
}
