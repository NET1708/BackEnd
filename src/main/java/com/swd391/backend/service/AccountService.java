package com.swd391.backend.service;

import com.swd391.backend.dao.UserRepository;
import com.swd391.backend.entity.ErrorMessage;
import com.swd391.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> register(User user) {

        // Check if username or email is already taken
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Username đã tồn tại!"));
        }

        // Check if email or email is already taken
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Email này đã được sử dụng!"));
        }

        // Save the user to the database
        userRepository.save(user);

        return ResponseEntity.ok().body("Đăng ký thành công!");
    }
}
