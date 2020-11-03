package com.accenture.javamos.service;

import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.repository.FlightRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
  @Autowired
  FlightRepository flightRepository;

  public List<Flight> findAll() {
    return flightRepository.findAll();
  }

  public Optional<Flight> findById(String id) {
    return flightRepository.findById(id);
  }

  public boolean exists(String id) {
    if (flightRepository.findById(id).isPresent()) return true;
    return false;
  }

  public Flight add(Flight flight) {
    return flightRepository.save(flight);
  }

  public Iterable<Flight> addAll(Iterable<Flight> flights) {
    return flightRepository.saveAll(flights);
  }
}
