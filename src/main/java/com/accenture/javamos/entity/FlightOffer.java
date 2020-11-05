package com.accenture.javamos.entity;

import com.amadeus.resources.FlightOfferSearch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "flight_offer")
public class FlightOffer extends FlightOfferSearch {

  @JsonIgnore
  @OneToOne
  private Flight flight;
}
