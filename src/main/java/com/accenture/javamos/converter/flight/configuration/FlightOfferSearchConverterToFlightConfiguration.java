// package com.accenture.javamos.converter.flight.configuration;

// import com.accenture.javamos.converter.flight.FlightConverter;
// import com.accenture.javamos.entity.Flight;
// import com.amadeus.resources.FlightOfferSearch;
// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.convert.converter.Converter;

// @Configuration
// public class FlightOfferSearchConverterToFlightConfiguration {

//   @Autowired
//   private FlightConverter flightConverter;

//   @Bean
//   public Converter<FlightOfferSearch[], List<Flight>> configFlightOfferSearchToFlightConverter() {
//     return flightConverter.toFlightList();
//   }
// }
