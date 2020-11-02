package com.accenture.javamos.converter.configuration;

import com.accenture.javamos.converter.FlightDateConverter;
import com.accenture.javamos.converter.FlightOfferSearchConverter;
import com.accenture.javamos.converter.FlightSegmentConverter;
import com.accenture.javamos.converter.IataCodeConverter;
import com.accenture.javamos.entity.Flight;
import com.amadeus.resources.FlightOfferSearch;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class FlightOfferSearchConverterConfiguration {
  @Autowired
  IataCodeConverter iataCodeConverter;

  @Autowired
  FlightSegmentConverter flightSegmentConverter;

  @Autowired
  FlightDateConverter dateConverter;

  @Bean
  public Converter<FlightOfferSearch[], Set<Flight>> configFlightOfferSearchConverter() {
    return new FlightOfferSearchConverter(
      this.iataCodeConverter,
      this.flightSegmentConverter,
      this.dateConverter
    );
  }
}
