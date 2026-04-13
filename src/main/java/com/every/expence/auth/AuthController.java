package com.every.expence.auth;

import com.every.expence.auth.dto.LoginRequestDTO;
import com.every.expence.auth.dto.LoginResponseDTO;
import com.every.expence.auth.dto.LogoutRequestDTO;
import com.every.expence.auth.dto.RefreshRequestDTO;
import com.every.expence.auth.dto.RegistrationRequestDTO;
import com.every.expence.user.User;

import jakarta.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO) {
        return authService.register(registrationRequestDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // NOTE: in a mobile app the refreshToken should be stored in an encrypted storage
        // Consider https://www.npmjs.com/package/react-native-encrypted-storage
        
        return authService.login(loginRequestDTO);
    }

    @PostMapping("/refresh")
    public String refreshToken(@RequestBody RefreshRequestDTO refreshRequestDTO) {
        return authService.refresh(refreshRequestDTO);
    }

    @GetMapping("/welcome")
    public String welcome(@AuthenticationPrincipal User user) {
        return "Welcome, " + user.getEmail();
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        authService.logout(logoutRequestDTO);
    }
    
}
