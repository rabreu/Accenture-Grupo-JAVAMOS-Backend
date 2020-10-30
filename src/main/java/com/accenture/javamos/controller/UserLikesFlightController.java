package com.accenture.javamos.controller;

import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.entity.User;
import com.accenture.javamos.entity.UserLikesFlight;
import com.accenture.javamos.service.FlightService;
import com.accenture.javamos.service.SecurityService;
import com.accenture.javamos.service.UserLikesFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/flight/like")
public class UserLikesFlightController {

    @Autowired
    UserLikesFlightService userLikesFlightService;

    @Autowired
    SecurityService securityService;

    @Autowired
    FlightService flightService;

    @PostMapping(value = "", params = {"id"})
    @ResponseBody
    public ResponseEntity<Object> UserLikesFlightController(@RequestParam("id") Integer flightId) {
        try {
            User user = securityService.getUserAuthenticated();
            Optional<Flight> flight = flightService.findById(flightId);

            if (!flight.isPresent())
                return new ResponseEntity<>("{\"status_code\": " + HttpStatus.INTERNAL_SERVER_ERROR.value() + ",\"message\": \"" + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);

            UserLikesFlight userLikesFlight = userLikesFlightService.add(flight.get(), user);
            return new ResponseEntity<>(userLikesFlight, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("{\"status_code\": " + HttpStatus.INTERNAL_SERVER_ERROR.value() + ",\"message\": \"" + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
