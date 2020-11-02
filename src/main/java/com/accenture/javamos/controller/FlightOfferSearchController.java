package com.accenture.javamos.controller;

import com.accenture.javamos.converter.FlightOfferSearchConverter;
import com.accenture.javamos.model.ErrorResponse;
import com.accenture.javamos.model.FlightOfferSearchResponse;
import com.accenture.javamos.service.FlightOfferSearchService;
import com.accenture.javamos.service.FlightService;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
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
  FlightOfferSearchConverter flightOfferSearchConverter;

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
      // get flight offers (Amadeus API)
      FlightOfferSearch[] flightOfferSearches = flightOfferSearchService.flightOfferSearchWithNoReturnDate(
        fromIataCode,
        toIataCode,
        departureDate,
        adults
      );

      // TODO: save flights to database

      FlightOfferSearchResponse response = new FlightOfferSearchResponse(
        // convert FlightOfferSearch[] to List<Flight>
        this.flightOfferSearchConverter.convert(flightOfferSearches)
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
