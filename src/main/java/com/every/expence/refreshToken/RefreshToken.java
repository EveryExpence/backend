package com.every.expence.refreshToken;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "refreshTokens")
public class RefreshToken {
    @Id
    private String tokenHash;

    @Indexed
    private String userId;

    @Indexed(expireAfter = "0s")
    private Instant expirationDate;

    public RefreshToken(String tokenHash, String userId, Instant expirationDate) {
        this.tokenHash = tokenHash;
        this.userId = userId;
        this.expirationDate = expirationDate;
    }

    public RefreshToken() {
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String token) {
        this.tokenHash = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expiryDate) {
        this.expirationDate = expiryDate;
    }
}
