package com.every.expence.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    private JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateAccessToken(String userId) {
        Instant expirationTime = Instant.now().plus(jwtConfig.getExpirationTime());
        return JWT.create()
                .withIssuer(jwtConfig.getIssuer())
                .withClaim("userId", userId)
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
    }

    public String extractUserIdFromAccessToken(String accessToken) {
        return JWT.require(Algorithm.HMAC256(jwtConfig.getSecret()))
                .build()
                .verify(accessToken)
                .getClaim("userId")
                .asString();
    }
}
