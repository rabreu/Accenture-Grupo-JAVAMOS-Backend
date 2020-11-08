package com.accenture.javamos.converter.flight;

import com.accenture.javamos.service.AirlineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FlightConverter {

  @Autowired
  private AirlineService airlineService;

  @Autowired
  private ModelMapper mapper;

  @Bean
  public FlightDateConverter toDate() {
    return new FlightDateConverter();
  }

  @Bean
  public FlightOfferSearchArrayToFlightListConverter toFlightList() {
    return new FlightOfferSearchArrayToFlightListConverter(
      toAirline(),
      toFlightSegment(),
      toDate()
    );
  }

  @Bean
  public FlightOrderToFlightOrderDTOConverter toFlightOrderDTO() {
    return new FlightOrderToFlightOrderDTOConverter(this, mapper);
  }

  @Bean
  public TravelerDTOListToJsonArrayConverter toTravelersJson() {
    return new TravelerDTOListToJsonArrayConverter();
  }

  @Bean
  public SearchSegmentToFlightSegmentConverter toFlightSegment() {
    return new SearchSegmentToFlightSegmentConverter(toDate(), toAirline());
  }

  @Bean
  public IataCodeToAirlineConverter toAirline() {
    return new IataCodeToAirlineConverter(airlineService);
  }
}
