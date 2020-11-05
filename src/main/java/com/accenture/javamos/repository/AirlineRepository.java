package com.accenture.javamos.repository;

import com.accenture.javamos.entity.Airline;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, String> {
  List<Airline> findAll();
  Optional<Airline> findById(String id);
}
