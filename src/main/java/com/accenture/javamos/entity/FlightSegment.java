package com.accenture.javamos.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "flight_segment")
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

  @Column
  @NotNull
  private String airlineName;

  @Column
  @NotNull
  private String duration;

  @Column
  @NotNull
  private Integer numberOfStops;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "flight_number")
  private Flight flight;
}
