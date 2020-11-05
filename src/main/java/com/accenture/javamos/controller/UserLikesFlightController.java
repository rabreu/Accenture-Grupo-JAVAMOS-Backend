package com.accenture.javamos.controller;

import java.util.List;
import java.util.Optional;

import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.entity.User;
import com.accenture.javamos.entity.UserLikesFlight;
import com.accenture.javamos.entity.UserLikesFlightId;
import com.accenture.javamos.model.ErrorResponse;
import com.accenture.javamos.service.FlightService;
import com.accenture.javamos.service.SecurityService;
import com.accenture.javamos.service.UserLikesFlightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight")
public class UserLikesFlightController {
    @Autowired
    UserLikesFlightService userLikesFlightService;

    @Autowired
    SecurityService securityService;

    @Autowired
    FlightService flightService;

    @PostMapping(value = "/like", params = {"id"})
    @ResponseBody
    public ResponseEntity<Object> userLikesFlightController(
            @RequestParam("id") String flightId
    ) {
        try {
            User user = securityService.getUserAuthenticated();
            Optional<Flight> flight = flightService.findById(flightId);

            if (!flight.isPresent()) {
                return new ResponseEntity<>(
                        new ErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                        ),
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            }

            UserLikesFlightId userLikesFlightId = new UserLikesFlightId(
                    user,
                    flight.get()
            );

            UserLikesFlight response = userLikesFlightService.add(
                    new UserLikesFlight(userLikesFlightId)
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

  @GetMapping(value = "/like")
  @ResponseBody
  public ResponseEntity<Object> userLikesFlightController() {
    try {
      User user = securityService.getUserAuthenticated();
      List<UserLikesFlight> response;
      response = userLikesFlightService.findByUserId(user.getId());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(
            new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
    );
  }
}
