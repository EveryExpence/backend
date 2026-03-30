package com.every.expence.auth;

import com.every.expence.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping("/welcome")
    public String welcome(@AuthenticationPrincipal User user) {
        return "Welcome, " + user.getEmail();
    }
}
