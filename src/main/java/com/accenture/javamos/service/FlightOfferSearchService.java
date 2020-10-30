package com.accenture.javamos.service;

import com.accenture.javamos.configuration.AmadeusConfig;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import org.springframework.stereotype.Service;

@Service
public class FlightOfferSearchService {
    Amadeus amadeus = Amadeus
            .builder(AmadeusConfig.CLIENT_ID, AmadeusConfig.CLIENT_SECRET)
            .build();

    public com.amadeus.resources.FlightOfferSearch[] flightOfferSearchWithNoReturnDate(String fromIataCode, String toIataCode, String departureDate, Integer adults) throws ResponseException {
        com.amadeus.resources.FlightOfferSearch[] flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", fromIataCode)
                        .and("destinationLocationCode", toIataCode)
                        .and("departureDate", departureDate)
                        .and("adults", adults));
//                        .and("max", 3));
        return flightOffersSearches;
    }

    public com.amadeus.resources.FlightOfferSearch[] flightOfferSearchWithReturnDate(String fromIataCode, String toIataCode, String departureDate, String returnDate, Integer adults) throws ResponseException {
        com.amadeus.resources.FlightOfferSearch[] flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", fromIataCode)
                        .and("destinationLocationCode", toIataCode)
                        .and("departureDate", departureDate)
                        .and("returnDate", returnDate)
                        .and("adults", adults));
//                        .and("max", 3));

        System.out.println(flightOffersSearches[0]);
        return flightOffersSearches;
    }
}
