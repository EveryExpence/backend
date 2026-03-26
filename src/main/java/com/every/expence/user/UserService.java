package com.every.expence.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User registerUser(UserRequestDTO userRequestDTO) {
        return userRepository.save(userRequestDTO.toEntity());
    }
}
