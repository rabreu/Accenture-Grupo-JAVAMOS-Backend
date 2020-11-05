package com.accenture.javamos.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "flight")
public class Flight {

  @Id
  private String id;

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
  private String duration;

  @Column
  @NotNull
  private String airlineName;

  @Column
  @NotNull
  private Integer numberOfBookableSeats;

  @Column
  @NotNull
  private String currency;

  @Column
  @NotNull
  private double totalPrice;

  @Column
  @NotNull
  private Integer numberOfStops;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "flight")
  private List<FlightSegment> segments;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "flight")
  private FlightOffer offer;
}
