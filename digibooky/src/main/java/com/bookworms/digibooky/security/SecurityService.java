package com.bookworms.digibooky.security;

import com.bookworms.digibooky.user.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SecurityService {

    private final Logger securityLogger = LoggerFactory.getLogger(SecurityService.class);
    private final UserRepository userRepository;

    private SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Role getRoleById(String id) {
        return userRepository.getUserById(id).getRole();
    }

    public boolean validateAuthorization(String id, Feature feature) {
        if (!(getRoleById(id).containsFeature(feature))) {
            securityLogger.error("User with id" + id + " is not authorized to " + feature.toString() + "!");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        securityLogger.info("Authorization confirmed.");
        return true;
    }
}