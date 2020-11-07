// package com.accenture.javamos.converter.flight.configuration;

// import com.accenture.javamos.converter.flight.FlightConverter;
// import com.accenture.javamos.entity.FlightSegment;
// import com.amadeus.resources.FlightOfferSearch.SearchSegment;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.convert.converter.Converter;

// @Configuration
// public class FlightSegmentConverterConfiguration {

//   @Autowired
//   private FlightConverter flightConverter;

//   @Bean
//   public Converter<SearchSegment, FlightSegment> configFlightSegmentConverter() {
//     return flightConverter.toFlightSegment();
//   }
// }
