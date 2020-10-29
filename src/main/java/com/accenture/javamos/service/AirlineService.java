package com.accenture.javamos.service;

import com.accenture.javamos.configuration.AmadeusConfig;
import com.amadeus.Amadeus;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Airline;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {

    Amadeus amadeus = Amadeus
            .builder(AmadeusConfig.CLIENT_ID, AmadeusConfig.CLIENT_SECRET)
            .build();

    public Airline[] getAllAirline() throws ResponseException {
        return amadeus.referenceData.airlines.get();
    }
}
