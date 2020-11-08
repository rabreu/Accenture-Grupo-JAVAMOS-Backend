package com.accenture.javamos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightOfferSearchRequest {
  private String from;
  private String to;
  private String departure;
  private String returnDate;
  private Integer adults;
}
