package com.accenture.javamos.repository;

import com.amadeus.resources.Airline;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Repository
@Getter
@Setter
public class AmadeusRepository {
  private Airline[] airlines;
}
