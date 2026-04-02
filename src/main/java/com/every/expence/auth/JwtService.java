package com.every.expence.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class JwtService {
    // TODO: in production, move these to a dedicated config
    private final String ACCESS_TOKEN_SECRET = "something base64 encoded";
    private final String REFRESH_TOKEN_SECRET = "something else base64 encoded";
    private final Duration ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(15);
    private final Duration REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofDays(7);
    private final String ISSUER = "EveryExpense";

    // TODO: save user id as a claim
    public String generateAccessToken(String email) {
        return generateToken(email, ACCESS_TOKEN_SECRET, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, REFRESH_TOKEN_SECRET, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String generateToken(String email, String secret, Duration ttl) {
        Instant expirationTime = Instant.now().plus(ttl);
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("email", email)
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC256(secret));
    }

    private String extractEmail(String token, String secret) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getClaim("email")
                .asString();
    }

    public String extractEmailFromAccessToken(String accessToken) {
        return extractEmail(accessToken, ACCESS_TOKEN_SECRET);
    }

    public String generateTokenFromRefreshToken(String refreshToken) {
        String email = extractEmail(refreshToken, REFRESH_TOKEN_SECRET);
        return generateAccessToken(email);
    }
}
