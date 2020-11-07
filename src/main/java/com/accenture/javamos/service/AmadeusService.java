package com.accenture.javamos.service;

import com.accenture.javamos.configuration.AmadeusConfig;
import com.accenture.javamos.model.FlightOfferSearchRequest;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightOrder;
import com.amadeus.resources.FlightPrice;
import com.amadeus.resources.Traveler;
import com.google.gson.JsonObject;

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
    FlightOfferSearchRequest search
  ) {
    try {
      return amadeus.shopping.flightOffersSearch.get(
        Params
          .with("originLocationCode", search.getFrom())
          .and("destinationLocationCode", search.getTo())
          .and("departureDate", search.getDeparture())
          .and("adults", search.getAdults())
          .and("currencyCode", "BRL")
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

  public FlightPrice getFlightPrice(FlightOfferSearch search) {
    try {
      return amadeus.shopping.flightOffersSearch.pricing.post(search);
    } catch (ResponseException e) {
      e.printStackTrace();
    }

    return null;
  }

  public FlightOrder createOrder(FlightPrice price, Traveler[] travelers) {
    try {
      return amadeus.booking.flightOrders.post(price, travelers);
    } catch (ResponseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public FlightOrder createOrder(JsonObject body) {
    try {
      return amadeus.booking.flightOrders.post(body);
    } catch (ResponseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
