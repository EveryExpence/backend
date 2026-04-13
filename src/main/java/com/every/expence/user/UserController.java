package com.every.expence.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.every.expence.user.dto.ChangeEmailRequestDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/changeEmail")
    public ResponseEntity<Void> changeEmail(@AuthenticationPrincipal User user,
            @Valid @RequestBody ChangeEmailRequestDTO changeEmailRequestDTO) {
        userService.changeEmail(user, changeEmailRequestDTO.email());

        return ResponseEntity.noContent().build();
    }

}
