package com.accenture.javamos.converter;

import com.accenture.javamos.entity.FlightSegment;
import com.amadeus.resources.FlightOfferSearch.SearchSegment;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FlightSegmentConverter
  implements Converter<SearchSegment, FlightSegment> {
  private final Converter<String, Date> flightDateConverter;

  @Override
  public FlightSegment convert(SearchSegment segment) {
    String number = segment.getCarrierCode() + segment.getNumber();
    String from = segment.getDeparture().getIataCode();
    String to = segment.getArrival().getIataCode();
    String departureDateString = segment.getDeparture().getAt();
    Date departureDate = this.flightDateConverter.convert(departureDateString);
    String arrivalDateString = segment.getArrival().getAt();
    Date arrivalDate = this.flightDateConverter.convert(arrivalDateString);
    String airlineName = segment.getCarrierCode();
    String duration = segment.getDuration();
    Integer numberOfStops = segment.getNumberOfStops();

    FlightSegment fSegment = new FlightSegment();
    fSegment.setNumber(number);
    fSegment.setOriginLocationCode(from);
    fSegment.setDestinationLocationCode(to);
    fSegment.setDepartureDate(departureDate);
    fSegment.setArrivalDate(arrivalDate);
    fSegment.setAirlineName(airlineName);
    fSegment.setDuration(duration);
    fSegment.setNumberOfStops(numberOfStops);

    return fSegment;
  }
}
