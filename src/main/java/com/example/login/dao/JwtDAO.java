package com.example.login.dao;

import com.example.login.map.JwtResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface JwtDAO {
    JwtResponse createJwtForClaims(String subject, Map<String, String> claims);

    JwtResponse refreshToken(String Token);
}
