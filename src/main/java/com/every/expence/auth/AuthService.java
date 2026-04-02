package com.every.expence.auth;

import com.every.expence.auth.dto.LoginRequestDTO;
import com.every.expence.auth.dto.LoginResponseDTO;
import com.every.expence.auth.dto.RefreshRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.email(),
                        loginRequestDTO.password()
                )
        );
        String accessToken = jwtService.generateToken(loginRequestDTO.email());
        String refreshToken = jwtService.generateRefreshToken(loginRequestDTO.email());
        return new LoginResponseDTO(accessToken, refreshToken);
    }

    public String refresh(RefreshRequestDTO refreshRequestDTO) {
        return jwtService.generateTokenFromRefreshToken(refreshRequestDTO.refreshToken());
    }
}
