package com.accenture.javamos.service;

import com.accenture.javamos.converter.flight.FlightConverter;
import com.accenture.javamos.dto.FlightOrderDTO;
import com.accenture.javamos.dto.TravelerDTO;
import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.model.FlightOfferSearchRequest;
import com.accenture.javamos.repository.FlightRepository;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightOrder;
import com.amadeus.resources.FlightPrice;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

  @Autowired
  FlightRepository flightRepository;

  @Autowired
  private AmadeusService amadeusService;

  @Autowired
  private FlightConverter flightConverter;

  public List<Flight> findAll() {
    return flightRepository.findAll();
  }

  public Optional<Flight> findById(String id) {
    return flightRepository.findById(id);
  }

  public boolean exists(String id) {
    if (flightRepository.findById(id).isPresent()) return true;
    return false;
  }

  public Flight add(Flight flight) {
    return flightRepository.save(flight);
  }

  public Iterable<Flight> addAll(Iterable<Flight> flights) {
    return flightRepository.saveAll(flights);
  }

  /** Amadeus */

  public List<Flight> flightOfferSearchWithNoReturnDate(
    FlightOfferSearchRequest search
  )
    throws ResponseException {
    com.amadeus.resources.FlightOfferSearch[] flightOffersSearches = amadeusService.getFlightOfferSearches(
      search
    );

    List<Flight> flights =
      this.flightConverter.toFlightList().convert(flightOffersSearches);

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

    return this.flightConverter.toFlightList().convert(flightOffersSearches);
  }

  public FlightPrice getFlightPrice(FlightOfferSearch offer) {
    return amadeusService.getFlightPrice(offer);
  }

  public FlightOrder createOrder(FlightOrderDTO order) {
    // get flight offer (stored as JSON)
    String offerJson =
      this.findById(order.getFlight().getId()).get().getOffer();

    // convert JSON to FlightOfferSearch
    FlightOfferSearch offer = new Gson()
    .fromJson(offerJson, FlightOfferSearch.class);

    // Confirm flight price
    FlightPrice price = this.getFlightPrice(offer);

    // convert flight orders with confirmed price to JSON: this is amadeus data
    // convert travelers to JSON: this is user data
    JsonArray flightOffersJson = new Gson()
      .toJsonTree(
        price.getFlightOffers(),
        new TypeToken<FlightOfferSearch[]>() {}.getType()
      )
      .getAsJsonArray();
    JsonArray travelersJson = new Gson()
      .toJsonTree(
        order.getTravelers(),
        new TypeToken<List<TravelerDTO>>() {}.getType()
      )
      .getAsJsonArray();

    // construct body with all data to send to Amadeus API
    JsonObject body = new JsonObject();
    JsonObject data = new JsonObject();

    body.add("data", data);
    data.addProperty("type", "flight-order");
    data.add("flightOffers", flightOffersJson);
    data.add("travelers", travelersJson);

    return amadeusService.createOrder(body);
  }
}
