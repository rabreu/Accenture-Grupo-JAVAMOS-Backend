package com.accenture.javamos.converter.flight;

import com.accenture.javamos.entity.Airline;
import com.accenture.javamos.service.AirlineService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Get Iata Code from Amadeus and convert it to our domain Airline
 */
@Component
@AllArgsConstructor
public class IataCodeToAirlineConverter implements Converter<String, Airline> {

  private final AirlineService airlineService;

  @Override
  public Airline convert(String iataCode) {
    return airlineService
      .getAirlines()
      .stream()
      .filter(a -> a.getId().equals(iataCode))
      .findFirst()
      .get();
  }
}
