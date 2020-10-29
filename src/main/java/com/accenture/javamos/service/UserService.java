package com.accenture.javamos.service;

import com.accenture.javamos.entity.User;
import com.accenture.javamos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User add(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        return userRepository.save(user);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User changePassword(Integer userId, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent() && bCryptPasswordEncoder.encode(oldPassword) == user.get().getPassword()) {
            user.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
            return userRepository.save(user.get());
        }

        return null;
    }
}
