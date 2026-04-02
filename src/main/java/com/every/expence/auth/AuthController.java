package com.every.expence.auth;

import com.every.expence.auth.dto.LoginRequestDTO;
import com.every.expence.auth.dto.LoginResponseDTO;
import com.every.expence.auth.dto.RefreshRequestDTO;
import com.every.expence.user.User;
import com.every.expence.user.UserRequestDTO;
import com.every.expence.user.UserResponseDTO;
import com.every.expence.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDTO registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        // TODO: add email/password validity checks
        User registeredUser = userService.registerUser(userRequestDTO);
        return UserResponseDTO.fromEntity(registeredUser);
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
}
