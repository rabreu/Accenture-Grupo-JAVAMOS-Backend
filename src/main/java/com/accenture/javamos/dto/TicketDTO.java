package com.accenture.javamos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketDTO {

  private String id;
  private String status;
  FlightOrderDTO flightOrder;
}
