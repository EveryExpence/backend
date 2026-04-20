package com.every.expence.auth;

import com.every.expence.auth.dto.LoginRequestDTO;
import com.every.expence.auth.dto.LoginResponseDTO;
import com.every.expence.auth.dto.LogoutRequestDTO;
import com.every.expence.auth.dto.RefreshRequestDTO;
import com.every.expence.auth.dto.RefreshResponseDTO;
import com.every.expence.auth.dto.RegistrationRequestDTO;
import com.every.expence.auth.jwt.JwtService;
import com.every.expence.auth.refreshToken.RefreshTokenService;
import com.every.expence.user.User;
import com.every.expence.user.UserService;

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
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    public String register(RegistrationRequestDTO registrationRequestDTO) {
        return userService.addUser(
            registrationRequestDTO.email(),
            registrationRequestDTO.password()
        ).getEmail();
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.email(),
                        loginRequestDTO.password()
                )
        );
        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user.getId());
        String refreshTokenString = refreshTokenService.generateRefreshTokenString();
        refreshTokenService.addRefreshToken(user.getId(), refreshTokenString);

        return new LoginResponseDTO(accessToken, refreshTokenString);
    }

    public RefreshResponseDTO refresh(RefreshRequestDTO refreshRequestDTO) {
        String userId = refreshTokenService
            .getUserIdFromRefreshToken(refreshRequestDTO.refreshToken())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        refreshTokenService.revokeRefreshToken(refreshRequestDTO.refreshToken());
        String refreshToken = refreshTokenService.generateRefreshTokenString();
        String accessToken = jwtService.generateAccessToken(userId);
        refreshTokenService.addRefreshToken(userId, refreshToken);
        return new RefreshResponseDTO(accessToken, refreshToken);
    }

    public void logout(LogoutRequestDTO logoutRequestDTO) {
        String refreshTokenString = logoutRequestDTO.refreshToken();
        refreshTokenService.revokeRefreshToken(refreshTokenString);
    }
}
