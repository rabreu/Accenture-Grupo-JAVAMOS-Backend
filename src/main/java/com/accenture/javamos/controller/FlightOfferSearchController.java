package com.accenture.javamos.controller;

import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.service.AirlineService;
import com.accenture.javamos.service.FlightOfferSearchService;
import com.accenture.javamos.service.FlightService;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Airline;
import com.amadeus.resources.FlightOfferSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/flight/search")
public class FlightOfferSearchController {

    @Autowired
    FlightOfferSearchService flightOfferSearchService;

    @Autowired
    FlightService flightService;

    @Autowired
    AirlineService airlineService;

    @GetMapping(path = "", params = {"from", "to", "departure", "adults"})
    public ResponseEntity<Object> flightOfferSearchWithNoReturnDate(@RequestParam("from") String fromIataCode,
                                                                    @RequestParam("to") String toIataCode,
                                                                    @RequestParam("departure") String departureDate,
                                                                    @RequestParam("adults") Integer adults) throws ResponseException {

        try {
            FlightOfferSearch[] flightOffersSearches = flightOfferSearchService.flightOfferSearchWithNoReturnDate(fromIataCode, toIataCode, departureDate, adults);
            List<Flight> flightsResponse = new ArrayList<Flight>();

            for (FlightOfferSearch f : flightOffersSearches) {
                int intineraryLength = f.getItineraries().length - 1;
                int segmentsLength = f.getItineraries()[intineraryLength].getSegments().length - 1;

                List<String> airlinesList = new ArrayList<String>();
                for (Airline a : airlineService.getAllAirline())
                    for (String s : f.getValidatingAirlineCodes())
                        if (a.getIataCode().equals(s))
                            airlinesList.add(a.getCommonName());

                Long generatedId = Instant.parse(f.getItineraries()[intineraryLength].getSegments()[segmentsLength].getDeparture().getAt() + "Z").getEpochSecond() + Instant.parse(f.getItineraries()[intineraryLength].getSegments()[segmentsLength].getArrival().getAt() + "Z").getEpochSecond();
                Flight flight = new Flight(
                        generatedId,
                        f.getItineraries()[0].getSegments()[0].getDeparture().getIataCode(),
                        f.getItineraries()[intineraryLength].getSegments()[segmentsLength].getArrival().getIataCode(),
                        (Date) Date.from(Instant.parse(f.getItineraries()[0].getSegments()[0].getDeparture().getAt() + "Z")),
                        (Date) Date.from(Instant.parse(f.getItineraries()[intineraryLength].getSegments()[segmentsLength].getArrival().getAt() + "Z")),
                        airlinesList.get(0),
                        f.getNumberOfBookableSeats(),
                        f.getPrice().getCurrency(),
                        f.getPrice().getGrandTotal()
                );

                if(!flightService.exists(flight.getId()))
                    flightService.add(flight);

                flightsResponse.add(flight);
            }
            return new ResponseEntity<>(flightsResponse, HttpStatus.OK);
        } catch (ResponseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("{\"status_code\": " + HttpStatus.INTERNAL_SERVER_ERROR.value() + ",\"message\": \"" + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
