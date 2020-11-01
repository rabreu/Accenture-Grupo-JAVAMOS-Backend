package com.accenture.javamos.service;

import com.accenture.javamos.configuration.AmadeusConfig;
import com.amadeus.Amadeus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmadeusService {

  public Amadeus amadeus;

  @Autowired
  public AmadeusService(@Autowired AmadeusConfig amadeusConfig) {
    this.amadeus = Amadeus.builder(amadeusConfig.CLIENT_ID, amadeusConfig.CLIENT_SECRET).build();
  }

}
