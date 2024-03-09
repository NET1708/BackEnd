package com.swd391.backend.service.Interface;

import com.swd391.backend.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    public User findByUsername(String username);
}
