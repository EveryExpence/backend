package com.every.expence.auth;

import com.every.expence.auth.dto.LoginRequestDTO;
import com.every.expence.auth.dto.LoginResponseDTO;
import com.every.expence.auth.dto.RefreshRequestDTO;

import org.apache.commons.lang3.NotImplementedException;
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
        String accessToken = jwtService.generateAccessToken(loginRequestDTO.email());
        // TODO: generate the refresh token as an opaque token, rathen than a jwt token
        return new LoginResponseDTO(accessToken, "");
    }

    public String refresh(RefreshRequestDTO refreshRequestDTO) {
        // TODO: check refresh token hash. If not expired, then generate a new access token
        throw new NotImplementedException();
    }
}
