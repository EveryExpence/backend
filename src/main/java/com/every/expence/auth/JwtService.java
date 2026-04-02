package com.every.expence.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@Service
public class JwtService {
    // TODO: in production, move these to a dedicated config
    private final String ACCESS_TOKEN_SECRET = "something base64 encoded";
    private final String REFRESH_TOKEN_SECRET = "something else base64 encoded";
    private final Duration ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(15);
    private final Duration REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofDays(7);
    private final String ISSUER = "EveryExpense";

    // TODO: save user id as a claim
    public String generateToken(String email) {
        Instant expirationTime = Instant.now().plus(ACCESS_TOKEN_EXPIRATION_TIME);
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("email", email)
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC256(ACCESS_TOKEN_SECRET));
    }

    public String generateRefreshToken(String email) {
        // TODO: refactor to follow the DRY principle
        Instant expirationTime = Instant.now().plus(REFRESH_TOKEN_EXPIRATION_TIME);
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("email", email)
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC256(REFRESH_TOKEN_SECRET));
    }

    public String extractEmail(String token) {
        return JWT.require(Algorithm.HMAC256(ACCESS_TOKEN_SECRET))
                .build()
                .verify(token)
                .getClaim("email")
                .asString();
    }

    public String generateTokenFromRefreshToken(String refreshToken) {
        // TODO: refactor to follow the DRY principle
        String email = JWT.require(Algorithm.HMAC256(REFRESH_TOKEN_SECRET))
                .build()
                .verify(refreshToken)
                .getClaim("email")
                .asString();
        return generateToken(email);
    }
}
