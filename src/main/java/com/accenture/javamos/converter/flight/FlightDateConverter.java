package com.accenture.javamos.converter.flight;

import java.time.Instant;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convert from String to Date (to store at DB)
 */
@Component
public class FlightDateConverter implements Converter<String, Date> {

  @Override
  public Date convert(String date) {
    return Date.from(Instant.parse(date + "Z"));
  }
}
