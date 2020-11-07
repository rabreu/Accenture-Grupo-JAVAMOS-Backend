package com.accenture.javamos.dto;

import com.accenture.javamos.entity.Airline;
import com.accenture.javamos.entity.FlightSegment;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightDTO {

  private String id;
  private String from;
  private String to;
  private Date departure;
  private Date arrival;
  private String duration;
  private Airline airline;
  private String currency;
  private double totalPrice;
  private Integer stops;
  private List<FlightSegment> segments;
}
