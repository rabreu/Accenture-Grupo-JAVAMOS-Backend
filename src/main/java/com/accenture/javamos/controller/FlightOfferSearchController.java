package com.accenture.javamos.controller;

import com.accenture.javamos.configuration.AmadeusConfig;
import com.accenture.javamos.model.FlightOfferSearchResponse;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Airline;
import com.amadeus.resources.FlightOfferSearch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/flights")
public class FlightOfferSearchController {

    @GetMapping(params = {"from", "to", "at", "adults"})
    public ResponseEntity<List<FlightOfferSearchResponse>> FlightOfferSearchController(@RequestParam("from") String fromIataCode,
                                                                   @RequestParam("to") String toIataCode,
                                                                   @RequestParam("at") String at,
                                                                   @RequestParam("adults") Integer adults) throws ResponseException {
        Amadeus amadeus = Amadeus
                .builder(AmadeusConfig.CLIENT_ID, AmadeusConfig.CLIENT_SECRET)
                .build();

        FlightOfferSearch[] flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", fromIataCode)
                        .and("destinationLocationCode", toIataCode)
                        .and("departureDate", at)
                        .and("adults", adults));
//                        .and("max", 3));

        Airline[] airlines = amadeus.referenceData.airlines.get();
        List<FlightOfferSearchResponse> flightOfferSearchResponse = new ArrayList<FlightOfferSearchResponse>();

        for (FlightOfferSearch f : flightOffersSearches) {
            int intineraryLength = f.getItineraries().length - 1;
            int segmentsLength = f.getItineraries()[intineraryLength].getSegments().length - 1;

            List<String> airlinesList = new ArrayList<String>();
            for(Airline a : airlines)
                for(String s : f.getValidatingAirlineCodes())
                    if(a.getIataCode().equals(s))
                        airlinesList.add(a.getCommonName());

            flightOfferSearchResponse.add(new FlightOfferSearchResponse(
                    f.getItineraries()[0].getSegments()[0].getDeparture().getIataCode(),
                    f.getItineraries()[intineraryLength].getSegments()[segmentsLength].getArrival().getIataCode(),
                    (Date) Date.from(Instant.parse(f.getItineraries()[0].getSegments()[0].getDeparture().getAt() + "Z")),
                    airlinesList,
                    f.isOneWay(),
                    f.getNumberOfBookableSeats(),
                    f.getPrice().getCurrency(),
                    f.getPrice().getGrandTotal()
            ));
        }
        return new ResponseEntity<List<FlightOfferSearchResponse>>(flightOfferSearchResponse, HttpStatus.OK);
    }
}
