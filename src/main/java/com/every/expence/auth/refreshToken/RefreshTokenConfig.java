package com.every.expence.auth.refreshToken;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "auth.refresh-token")
public class RefreshTokenConfig {
    private Duration expirationTime;

    public Duration getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Duration expirationTime) {
        this.expirationTime = expirationTime;
    }

}
