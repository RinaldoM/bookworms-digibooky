package com.bookworms.digibooky.security;

import com.bookworms.digibooky.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Role getRoleById(String id) {
        return userRepository.getUserById(id).getRole();
    }

    public boolean validateAuthorization(String id, Feature feature) {
        return getRoleById(id).containsFeature(feature);
    }
}