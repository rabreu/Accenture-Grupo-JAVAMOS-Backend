package com.accenture.javamos.service;


import com.accenture.javamos.entity.User;
import com.accenture.javamos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private UserRepository userRepository;

    @Autowired
    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserAuthenticated() {
        Authentication  authentication =  SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userRepository.findUserByEmail(name).get();
    }
}
