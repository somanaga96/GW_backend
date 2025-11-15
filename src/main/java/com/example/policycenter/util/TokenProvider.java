package com.example.policycenter.util;

import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private final JwtUtil jwtUtil;

    public TokenProvider(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String createToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public boolean isValid(String token) {
        return jwtUtil.validateToken(token);
    }

    public String getUser(String token) {
        return jwtUtil.extractUsername(token);
    }
}
