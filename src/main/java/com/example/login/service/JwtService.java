package com.example.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.login.dao.JwtDAO;
import com.example.login.map.JwtResponse;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service("jwtService")
public class JwtService implements JwtDAO {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtService(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    @Override
    public JwtResponse createJwtForClaims(String subject, Map<String, String> claims) {
        JwtResponse jwtResponse = new JwtResponse();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Instant.now().toEpochMilli());
        calendar.add(Calendar.MINUTE, 30);

        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);

        claims.forEach(jwtBuilder::withClaim);
        jwtResponse.setAccessToken(jwtBuilder
                .withNotBefore(new Date())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.RSA256(publicKey, privateKey)));

        calendar.add(Calendar.MINUTE, 33);
        jwtResponse.setRefreshToken(jwtBuilder
                .withNotBefore(new Date())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.RSA256(publicKey, privateKey)));

        return jwtResponse;

    }

    @Override
    public JwtResponse refreshToken(String Token) {
        return null;
    }
}
