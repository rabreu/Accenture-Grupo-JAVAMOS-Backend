package com.accenture.javamos.converter;

import com.accenture.javamos.entity.Airline;
import com.accenture.javamos.service.AirlineService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IataCodeConverter implements Converter<String, Airline> {

  @Autowired
  private AirlineService airlineService;

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
