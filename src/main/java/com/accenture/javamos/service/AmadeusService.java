package com.accenture.javamos.service;

import com.accenture.javamos.configuration.AmadeusConfig;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmadeusService {

  private Amadeus amadeus;

  @Autowired
  public AmadeusService(@Autowired AmadeusConfig amadeusConfig) {
    String clientId = amadeusConfig.getClientId();
    String clientSecret = amadeusConfig.getClientSecret();
    this.amadeus = Amadeus.builder(clientId, clientSecret).build();
  }

  public FlightOfferSearch[] getFlightOfferSearches(
    String fromIataCode,
    String toIataCode,
    String departureDate,
    Integer adults
  ) {
    try {
      return amadeus.shopping.flightOffersSearch.get(
        Params
          .with("originLocationCode", fromIataCode)
          .and("destinationLocationCode", toIataCode)
          .and("departureDate", departureDate)
          .and("adults", adults)
      );
    } catch (ResponseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public FlightOfferSearch[] getFlightOfferSearchesWithReturnDate(
    String fromIataCode,
    String toIataCode,
    String departureDate,
    String returnDate,
    Integer adults
  ) {
    try {
      return amadeus.shopping.flightOffersSearch.get(
        Params
          .with("originLocationCode", fromIataCode)
          .and("destinationLocationCode", toIataCode)
          .and("departureDate", departureDate)
          .and("returnDate", returnDate)
          .and("adults", adults)
      );
    } catch (ResponseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
