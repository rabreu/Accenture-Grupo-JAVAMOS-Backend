package com.accenture.javamos.service;

import com.accenture.javamos.converter.FlightOfferSearchConverter;
import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.repository.FlightRepository;
import com.amadeus.exceptions.ResponseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightOfferSearchService {
  @Autowired
  private AmadeusService amadeusService;

  @Autowired
  FlightOfferSearchConverter flightOfferSearchConverter;

  @Autowired
  FlightRepository flightRepository;

  public List<Flight> flightOfferSearchWithNoReturnDate(
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

    List<Flight> flights =
      this.flightOfferSearchConverter.convert(flightOffersSearches);

    flights.forEach(flightRepository::save);

    return flights;
  }

  public List<Flight> flightOfferSearchWithReturnDate(
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

    return this.flightOfferSearchConverter.convert(flightOffersSearches);
  }
}
