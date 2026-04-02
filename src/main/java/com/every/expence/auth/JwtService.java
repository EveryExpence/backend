package com.every.expence.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class JwtService {
    // TODO: in production, move these to a dedicated config
    private final String ACCESS_TOKEN_SECRET = "something base64 encoded";
    private final String REFRESH_TOKEN_SECRET = "something else base64 encoded";
    private final int VALID_TIME = 8;
    private final String ISSUER = "EveryExpense";

    public String generateToken(String email) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, VALID_TIME);
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("email", email)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(ACCESS_TOKEN_SECRET));
    }

    public String extractEmail(String token) {
        return JWT.require(Algorithm.HMAC256(ACCESS_TOKEN_SECRET))
                .build()
                .verify(token)
                .getClaim("email")
                .asString();
    }

    public String generateTokenFromRefreshToken(String refreshToken) {
        String email = JWT.require(Algorithm.HMAC256(REFRESH_TOKEN_SECRET))
                .build()
                .verify(refreshToken)
                .getClaim("email")
                .asString();
        return generateToken(email);
    }
}
