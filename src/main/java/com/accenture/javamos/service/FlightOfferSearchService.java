package com.accenture.javamos.service;

import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightOfferSearchService {

        @Autowired
        private AmadeusService amadeusService;

        public com.amadeus.resources.FlightOfferSearch[] flightOfferSearchWithNoReturnDate(String fromIataCode,
                        String toIataCode, String departureDate, Integer adults) throws ResponseException {
                com.amadeus.resources.FlightOfferSearch[] flightOffersSearches = amadeusService.amadeus.shopping.flightOffersSearch
                                .get(Params.with("originLocationCode", fromIataCode)
                                                .and("destinationLocationCode", toIataCode)
                                                .and("departureDate", departureDate).and("adults", adults));
                // .and("max", 3));
                return flightOffersSearches;
        }

        public com.amadeus.resources.FlightOfferSearch[] flightOfferSearchWithReturnDate(String fromIataCode,
                        String toIataCode, String departureDate, String returnDate, Integer adults)
                        throws ResponseException {
                com.amadeus.resources.FlightOfferSearch[] flightOffersSearches = amadeusService.amadeus.shopping.flightOffersSearch
                                .get(Params.with("originLocationCode", fromIataCode)
                                                .and("destinationLocationCode", toIataCode)
                                                .and("departureDate", departureDate).and("returnDate", returnDate)
                                                .and("adults", adults));
                // .and("max", 3));

                System.out.println(flightOffersSearches[0]);
                return flightOffersSearches;
        }
}
