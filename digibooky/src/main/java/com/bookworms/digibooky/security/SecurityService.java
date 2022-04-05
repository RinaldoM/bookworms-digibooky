package com.bookworms.digibooky.security;

import com.bookworms.digibooky.user.domain.User;
import com.bookworms.digibooky.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Role getRole(String id) {
        return userRepository.getUserById(id).getRole();
    }

    public boolean validateAuthorization(User user, Feature registerLibrarian) {
        return user.getRole().containsFeature(registerLibrarian);
    }
}