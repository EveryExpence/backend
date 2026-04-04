package com.every.expence.auth;

import com.every.expence.auth.dto.LoginRequestDTO;
import com.every.expence.auth.dto.LoginResponseDTO;
import com.every.expence.auth.dto.RefreshRequestDTO;
import com.every.expence.refreshToken.RefreshTokenService;
import com.every.expence.user.User;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.email(),
                        loginRequestDTO.password()
                )
        );
        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(loginRequestDTO.email());
        String refreshTokenString = refreshTokenService.generateRefreshTokenString();
        refreshTokenService.saveRefreshToken(user.getId(), refreshTokenString);

        return new LoginResponseDTO(accessToken, refreshTokenString);
    }

    public String refresh(RefreshRequestDTO refreshRequestDTO) {
        String userId = refreshTokenService.getUserIdFromRefreshToken(refreshRequestDTO.refreshToken());
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return jwtService.generateAccessToken(userId);
    }
}
