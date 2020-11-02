package com.accenture.javamos.converter;

import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.entity.FlightSegment;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightOfferSearch.Itinerary;
import com.amadeus.resources.FlightOfferSearch.SearchSegment;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FlightOfferSearchConverter
  implements Converter<FlightOfferSearch[], Set<Flight>> {
  private final Converter<String[], String> iataCodeConverter;
  private final Converter<SearchSegment, FlightSegment> flightSegmentConverter;
  private final Converter<String, Date> flightDateConverter;

  @Override
  public Set<Flight> convert(FlightOfferSearch[] flightOfferSearchs) {
    Set<Flight> flights = new HashSet<>();

    for (FlightOfferSearch f : flightOfferSearchs) {
      // we are converting a FlightOfferSearch to a Flight
      Flight flight = new Flight();

      // prepare some reusable variables
      Itinerary[] fItineraries = f.getItineraries();
      SearchSegment[] fSegments = fItineraries[0].getSegments();
      int segmentsLength = fSegments.length - 1;
      SearchSegment firstSegment = fSegments[0];
      SearchSegment lastSegment = fSegments[segmentsLength];

      // get airline name
      String airlineName =
        this.iataCodeConverter.convert(f.getValidatingAirlineCodes());

      // get segments and count total stops
      Set<FlightSegment> segments = new HashSet<>();
      int numberOfStops = 0;

      for (SearchSegment s : fSegments) {
        FlightSegment segment = this.flightSegmentConverter.convert(s);
        segment.setFlight(flight);
        segments.add(segment);
        numberOfStops += segment.getNumberOfStops();
      }

      // get flight number (e.g., AD7755)
      String number = firstSegment.getCarrierCode() + firstSegment.getNumber();

      // from/to code
      String originLocationCode = firstSegment.getDeparture().getIataCode();
      String destinationLocationCode = lastSegment.getArrival().getIataCode();

      // get duration by time diff (arrival minus departure)
      String departure = firstSegment.getDeparture().getAt();
      Date departureDate = this.flightDateConverter.convert(departure);
      String arrival = lastSegment.getArrival().getAt();
      Date arrivalDate = this.flightDateConverter.convert(arrival);
      long millis = arrivalDate.getTime() - departureDate.getTime();
      int hours = (int) ((millis / (1000 * 60 * 60)));
      int mins = (int) ((millis / (1000 * 60)) % 60);
      String duration = hours + "h" + mins + "min";

      // rest of info
      int numberOfBookableSeats = f.getNumberOfBookableSeats();
      String currency = f.getPrice().getCurrency();
      double totalPrice = f.getPrice().getGrandTotal();

      flight.setNumber(number);
      flight.setOriginLocationCode(originLocationCode);
      flight.setDestinationLocationCode(destinationLocationCode);
      flight.setDepartureDate(departureDate);
      flight.setArrivalDate(arrivalDate);
      flight.setDuration(duration);
      flight.setAirlineName(airlineName);
      flight.setNumberOfBookableSeats(numberOfBookableSeats);
      flight.setCurrency(currency);
      flight.setTotalPrice(totalPrice);
      flight.setNumberOfStops(numberOfStops);
      flight.setSegments(segments);

      flights.add(flight);
    }

    return flights;
  }
}
