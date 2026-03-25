package com.every.expence.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
