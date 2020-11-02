package com.accenture.javamos.controller;

import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.model.ErrorResponse;
import com.accenture.javamos.model.FlightOfferSearchResponse;
import com.accenture.javamos.service.FlightOfferSearchService;
import com.accenture.javamos.service.FlightService;
import com.amadeus.exceptions.ResponseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight/search")
public class FlightOfferSearchController {
  @Autowired
  FlightOfferSearchService flightOfferSearchService;

  @Autowired
  FlightService flightService;

  @GetMapping(path = "", params = { "from", "to", "departure", "adults" })
  public ResponseEntity<?> flightOfferSearchWithNoReturnDate(
    @RequestParam("from") String fromIataCode,
    @RequestParam("to") String toIataCode,
    @RequestParam("departure") String departureDate,
    @RequestParam("adults") Integer adults
  )
    throws ResponseException {
    try {
      List<Flight> flights = flightOfferSearchService.flightOfferSearchWithNoReturnDate(
        fromIataCode,
        toIataCode,
        departureDate,
        adults
      );

      FlightOfferSearchResponse response = new FlightOfferSearchResponse(
        flights
      );

      return new ResponseEntity<FlightOfferSearchResponse>(
        response,
        HttpStatus.OK
      );
    } catch (ResponseException e) {
      e.printStackTrace();
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
