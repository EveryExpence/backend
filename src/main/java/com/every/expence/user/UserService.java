package com.every.expence.user;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(String email, String password) {
        String passwordHash = passwordEncoder.encode(password);
        User user = new User(email, passwordHash);
        return userRepository.save(user);
    }

    public void changeEmail(User user, String email) {
        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);
        String normalizedCurrentEmail = user.getEmail().trim().toLowerCase(Locale.ROOT);

        if (normalizedCurrentEmail.equals(normalizedEmail)) {
            return;
        }

        boolean takenByAnotherUser = userRepository.findByEmail(normalizedEmail)
                .filter(found -> !found.getId().equals(user.getId())).isPresent();

        if (takenByAnotherUser) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        user.setEmail(normalizedEmail);
        userRepository.save(user);
    }

    public void changePassword(User user, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Current password is incorrect");
        }

        if (passwordEncoder.matches(newPassword, user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
