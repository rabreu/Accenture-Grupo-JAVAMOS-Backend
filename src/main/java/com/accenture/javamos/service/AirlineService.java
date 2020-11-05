package com.accenture.javamos.service;

import com.accenture.javamos.entity.Airline;
import com.accenture.javamos.repository.AirlineRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {

  @Autowired
  private AirlineRepository amadeusRepository;

  public List<Airline> getAirlines() {
    return this.amadeusRepository.findAll();
  }
}
