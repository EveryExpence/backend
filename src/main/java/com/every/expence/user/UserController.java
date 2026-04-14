package com.every.expence.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.every.expence.user.dto.ChangeAvatarRequestDTO;
import com.every.expence.user.dto.ChangeEmailRequestDTO;
import com.every.expence.user.dto.ChangePasswordRequestDTO;
import com.every.expence.user.dto.ChangePublicUsernameRequestDTO;
import com.every.expence.user.dto.GetUserDataDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public GetUserDataDTO getUserData(@AuthenticationPrincipal User user) {
        return new GetUserDataDTO(
            user.getEmail(),
            user.getPublicUsername(),
            user.getAvatarUrl()
        );
    }

    @PostMapping("/changeEmail")
    public ResponseEntity<Void> changeEmail(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChangeEmailRequestDTO changeEmailRequestDTO) {
        userService.changeEmail(user, changeEmailRequestDTO.email());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        userService.changePassword(user, changePasswordRequestDTO.oldPassword(),
                changePasswordRequestDTO.newPassword());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/changePublicUsername")
    public ResponseEntity<Void> changeUsername(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChangePublicUsernameRequestDTO changePublicUsernameRequestDTO) {
        userService.changePublicUsername(user, changePublicUsernameRequestDTO.newPublicUsername());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/changeAvatar")
    public ResponseEntity<Void> changeAvatar(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChangeAvatarRequestDTO changeAvatarRequestDTO) {
        userService.changeAvatarUrl(user, changeAvatarRequestDTO.avatarUrl());

        return ResponseEntity.noContent().build();
    }

}
