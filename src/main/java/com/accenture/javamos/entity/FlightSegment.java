package com.accenture.javamos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class FlightSegment {

  @Id
  private String number;

  @Column
  @NotNull
  private String originLocationCode;

  @Column
  @NotNull
  private String destinationLocationCode;

  @Column
  @NotNull
  private Date departureDate;

  @Column
  @NotNull
  private Date arrivalDate;

  @ManyToOne
  @NotNull
  private Airline airline;

  @Column
  @NotNull
  private String duration;

  @Column
  @NotNull
  private Integer numberOfStops;

  @JsonIgnore
  @ManyToOne
  private Flight flight;
}
