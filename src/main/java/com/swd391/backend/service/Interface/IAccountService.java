package com.swd391.backend.service.Interface;

import com.swd391.backend.entity.User;
import org.springframework.http.ResponseEntity;

public interface IAccountService {
    public ResponseEntity<?> register(User user);
}
