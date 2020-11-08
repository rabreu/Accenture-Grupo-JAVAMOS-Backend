package com.accenture.javamos.controller;

import com.accenture.javamos.controller.exception.FlightNotFoundException;
import com.accenture.javamos.controller.exception.UnauthorizedException;
import com.accenture.javamos.converter.user.UserConverter;
import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.entity.User;
import com.accenture.javamos.entity.UserLikesFlight;
import com.accenture.javamos.entity.UserLikesFlightId;
import com.accenture.javamos.service.FlightService;
import com.accenture.javamos.service.SecurityService;
import com.accenture.javamos.service.UserLikesFlightService;
import java.util.List;
import java.util.Optional;
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
  UserConverter userConverter;

  @Autowired
  SecurityService securityService;

  @Autowired
  FlightService flightService;

  @PostMapping(value = "/like", params = { "id" })
  @ResponseBody
  public ResponseEntity<Object> userLikesFlightController(
    @RequestParam("id") String flightId
  )
    throws UnauthorizedException, FlightNotFoundException {
    User user = securityService.getUserAuthenticated();
    Optional<Flight> flight = flightService.findById(flightId);

    if (!flight.isPresent()) {
      throw new FlightNotFoundException(
        "Passagem com o id " + flightId + " não foi encontrada"
      );
    }

    UserLikesFlightId userLikesFlightId = new UserLikesFlightId(
      user,
      flight.get()
    );

    UserLikesFlight response = userLikesFlightService.add(
      new UserLikesFlight(userLikesFlightId)
    );
    return new ResponseEntity<>(
      response.getId().getFlight(),
      HttpStatus.CREATED
    );
  }

  @GetMapping(value = "/like")
  @ResponseBody
  public ResponseEntity<Object> userLikesFlightController()
    throws UnauthorizedException {
    User user = securityService.getUserAuthenticated();
    List<UserLikesFlight> response;
    response = userLikesFlightService.findByUserId(user.getId());
    return new ResponseEntity<>(
      userConverter.toFlightsLikedList().convert(response),
      HttpStatus.OK
    );
  }
}
