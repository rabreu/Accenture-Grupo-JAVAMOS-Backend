package com.accenture.javamos.converter.flight;

import com.accenture.javamos.entity.Airline;
import com.accenture.javamos.entity.FlightSegment;
import com.amadeus.resources.FlightOfferSearch.SearchSegment;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Receive SearchSegment from Amadeus and convert it to our domain FlightSegment
 */
@Component
@AllArgsConstructor
public class SearchSegmentToFlightSegmentConverter
  implements Converter<SearchSegment, FlightSegment> {

  private final Converter<String, Date> toDate;
  private final Converter<String, Airline> toAirline;

  @Override
  public FlightSegment convert(SearchSegment segment) {
    String number = segment.getCarrierCode() + segment.getNumber();
    String from = segment.getDeparture().getIataCode();
    String to = segment.getArrival().getIataCode();
    String departureDateString = segment.getDeparture().getAt();
    Date departureDate = this.toDate.convert(departureDateString);
    String arrivalDateString = segment.getArrival().getAt();
    Date arrivalDate = this.toDate.convert(arrivalDateString);
    Airline airline = toAirline.convert(segment.getCarrierCode());
    String duration = segment.getDuration();
    Integer numberOfStops = segment.getNumberOfStops();

    FlightSegment fSegment = new FlightSegment();
    fSegment.setNumber(number);
    fSegment.setOriginLocationCode(from);
    fSegment.setDestinationLocationCode(to);
    fSegment.setDepartureDate(departureDate);
    fSegment.setArrivalDate(arrivalDate);
    fSegment.setAirline(airline);
    fSegment.setDuration(duration);
    fSegment.setNumberOfStops(numberOfStops);

    return fSegment;
  }
}
