package com.every.expence.auth.jwt;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtConfig {
    private String secret;
    private Duration expirationTime;
    private String issuer;
    
    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public Duration getExpirationTime() {
        return expirationTime;
    }
    public void setExpirationTime(Duration expirationTime) {
        this.expirationTime = expirationTime;
    }
    public String getIssuer() {
        return issuer;
    }
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
