package com.accenture.javamos.service;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Airline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {

    @Autowired
    AmadeusService amadeusService;

    public Airline[] getAllAirline() throws ResponseException {
        return amadeusService.amadeus.referenceData.airlines.get();
    }
}
