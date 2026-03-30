package com.every.expence.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class JwtService {
    // TODO: in production, move these to a dedicated config
    private final String SECRET = "something base64 encoded";
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
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String extractEmail(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token)
                .getClaim("email")
                .asString();
    }
}
