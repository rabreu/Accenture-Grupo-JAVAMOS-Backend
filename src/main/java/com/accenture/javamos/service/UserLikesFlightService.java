package com.accenture.javamos.service;

import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.entity.User;
import com.accenture.javamos.entity.UserLikesFlight;
import com.accenture.javamos.repository.UserLikesFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLikesFlightService {

    @Autowired
    UserLikesFlightRepository userLikesFlightRepository;

    public UserLikesFlight add(UserLikesFlight userlikesFlight) {
        return userLikesFlightRepository.save(userlikesFlight);
    }

    public UserLikesFlight add(Flight flight, User user) {
        UserLikesFlight userlikesFlight = new UserLikesFlight();
        userlikesFlight.setFlight(flight);
        userlikesFlight.setUser(user);
        return userLikesFlightRepository.save(userlikesFlight);
    }

    public boolean exists(Integer id) {
        if(userLikesFlightRepository.findById(id).isPresent())
            return true;
        return false;
    }
}
