package com.accenture.javamos.repository;

import com.accenture.javamos.entity.Flight;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
  List<Flight> findAll();

  Optional<Flight> findById(String id);
}
