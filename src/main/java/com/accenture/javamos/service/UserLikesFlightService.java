package com.accenture.javamos.service;

import com.accenture.javamos.entity.UserLikesFlight;
import com.accenture.javamos.repository.UserLikesFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLikesFlightService {
    @Autowired
    UserLikesFlightRepository userLikesFlightRepository;

    public UserLikesFlight add(UserLikesFlight userlikesFlight) {
        return userLikesFlightRepository.save(userlikesFlight);
    }

    public List<UserLikesFlight> findByUserId(Integer userId) {
        return userLikesFlightRepository.findByUserId(userId);
    }
}
