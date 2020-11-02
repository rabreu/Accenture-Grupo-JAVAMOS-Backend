package com.accenture.javamos.service;

import com.amadeus.exceptions.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightOfferSearchService {
  @Autowired
  private AmadeusService amadeusService;

  public com.amadeus.resources.FlightOfferSearch[] flightOfferSearchWithNoReturnDate(
    String fromIataCode,
    String toIataCode,
    String departureDate,
    Integer adults
  )
    throws ResponseException {
    com.amadeus.resources.FlightOfferSearch[] flightOffersSearches = amadeusService.getFlightOfferSearches(
      fromIataCode,
      toIataCode,
      departureDate,
      adults
    );

    return flightOffersSearches;
  }

  public com.amadeus.resources.FlightOfferSearch[] flightOfferSearchWithReturnDate(
    String fromIataCode,
    String toIataCode,
    String departureDate,
    String returnDate,
    Integer adults
  ) {
    com.amadeus.resources.FlightOfferSearch[] flightOffersSearches = amadeusService.getFlightOfferSearchesWithReturnDate(
      fromIataCode,
      toIataCode,
      departureDate,
      returnDate,
      adults
    );

    return flightOffersSearches;
  }
}
