package com.accenture.javamos.model;

import com.accenture.javamos.entity.Flight;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightOfferSearchResponse {
  Set<Flight> flights;
}
