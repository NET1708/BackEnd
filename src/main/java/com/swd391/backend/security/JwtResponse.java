package com.swd391.backend.security;

import lombok.AllArgsConstructor;
import lombok.Data;

public class JwtResponse {
    private final String jwt;

    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
