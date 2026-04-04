package com.every.expence.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class JwtService {
    private final String ACCESS_TOKEN_SECRET = "something base64 encoded";
    private final Duration ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(15);
    private final String ISSUER = "EveryExpense";

    public String generateAccessToken(String userId) {
        Instant expirationTime = Instant.now().plus(ACCESS_TOKEN_EXPIRATION_TIME);
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("userId", userId)
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC256(ACCESS_TOKEN_SECRET));
    }

    public String extractUserIdFromAccessToken(String accessToken) {
        return JWT.require(Algorithm.HMAC256(ACCESS_TOKEN_SECRET))
                .build()
                .verify(accessToken)
                .getClaim("userId")
                .asString();
    }
}
