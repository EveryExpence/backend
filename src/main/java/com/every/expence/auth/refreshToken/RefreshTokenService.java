package com.every.expence.auth.refreshToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    private static final Duration REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofDays(30);
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateRefreshTokenString() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return encoder.encodeToString(bytes);
    }

    public void saveRefreshToken(String userId, String refreshTokenString) {
        String tokenHash = hashToken(refreshTokenString);
        Instant expirationTime = Instant.now().plus(REFRESH_TOKEN_EXPIRATION_TIME);

        RefreshToken refreshToken = new RefreshToken(tokenHash, userId, expirationTime);
        refreshTokenRepository.save(refreshToken);
    }

    public Optional<String> getUserIdFromRefreshToken(String refreshTokenString) {
        String tokenHash = hashToken(refreshTokenString);
        RefreshToken refreshToken = refreshTokenRepository.findById(tokenHash).orElse(null);
        if (refreshToken == null) {
            return Optional.empty();
        }
        return Optional.of(refreshToken.getUserId());
    }

    public void revokeRefreshToken(String refreshTokenString) {
        String tokenHash = hashToken(refreshTokenString);
        refreshTokenRepository.deleteById(tokenHash);
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return encoder.encodeToString(digest.digest(token.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
