package com.accenture.javamos.converter.flight;

import com.accenture.javamos.entity.Airline;
import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.entity.FlightSegment;
import com.amadeus.Response;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightOfferSearch.Itinerary;
import com.amadeus.resources.FlightOfferSearch.SearchSegment;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Receive FlightOfferSearch[] from Amadeus and convert it to our domain List<Flight>
 */
@Component
@AllArgsConstructor
public class FlightOfferSearchArrayToFlightListConverter
  implements Converter<FlightOfferSearch[], List<Flight>> {

  private final Converter<String, Airline> toAirline;
  private final Converter<SearchSegment, FlightSegment> toFlightSegment;
  private final Converter<String, Date> toDate;

  @Override
  public List<Flight> convert(FlightOfferSearch[] flightOfferSearchs) {
    List<Flight> flights = new ArrayList<>();

    JsonArray flightOfferSearchesJSON = null;
    Response response = flightOfferSearchs[0].getResponse();
    int idx = 0;
    if (response != null) {
      flightOfferSearchesJSON = response.getData().getAsJsonArray();
    }

    for (FlightOfferSearch f : flightOfferSearchs) {
      // we are converting a FlightOfferSearch to a Flight
      Flight flight = new Flight();
      if (flightOfferSearchesJSON != null) {
        JsonElement offer = flightOfferSearchesJSON.get(idx++);
        flight.setOffer(offer.toString());
      }

      // prepare some reusable variables
      Itinerary[] fItineraries = f.getItineraries();
      SearchSegment[] fSegments = fItineraries[0].getSegments();
      int segmentsLength = fSegments.length - 1;
      SearchSegment firstSegment = fSegments[0];
      SearchSegment lastSegment = fSegments[segmentsLength];

      // get airline name
      Airline airline = this.toAirline.convert(firstSegment.getCarrierCode());

      // get segments and count total stops
      List<FlightSegment> segments = new ArrayList<>();
      int numberOfStops = 0;

      for (SearchSegment s : fSegments) {
        FlightSegment segment = this.toFlightSegment.convert(s);
        segment.setFlight(flight);
        segments.add(segment);
        numberOfStops += segment.getNumberOfStops();
      }

      // from/to code
      String originLocationCode = firstSegment.getDeparture().getIataCode();
      String destinationLocationCode = lastSegment.getArrival().getIataCode();

      // get duration by time diff (arrival minus departure)
      String departure = firstSegment.getDeparture().getAt();
      Date departureDate = this.toDate.convert(departure);
      String arrival = lastSegment.getArrival().getAt();
      Date arrivalDate = this.toDate.convert(arrival);
      long millis = arrivalDate.getTime() - departureDate.getTime();
      int hours = (int) ((millis / (1000 * 60 * 60)));
      int mins = (int) ((millis / (1000 * 60)) % 60);
      String duration = String.format("%02dh%02dmin", hours, mins);

      DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

      // id + origin + dest + departure date + arrival date (e.g., 0004-BSB-CGN-20201111-20201111)
      String id =
        String.format("%04d", Integer.parseInt(f.getId())) +
        "-" +
        firstSegment.getDeparture().getIataCode() +
        "-" +
        lastSegment.getArrival().getIataCode() +
        "-" +
        dateFormat.format(departureDate) +
        "-" +
        dateFormat.format(arrivalDate);

      // rest of info
      int numberOfBookableSeats = f.getNumberOfBookableSeats();
      String currency = f.getPrice().getCurrency();
      double totalPrice = f.getPrice().getGrandTotal();

      flight.setId(id);
      flight.setOriginLocationCode(originLocationCode);
      flight.setDestinationLocationCode(destinationLocationCode);
      flight.setDepartureDate(departureDate);
      flight.setArrivalDate(arrivalDate);
      flight.setDuration(duration);
      flight.setAirline(airline);
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
