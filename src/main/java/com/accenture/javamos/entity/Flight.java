package com.accenture.javamos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Flights")
public class Flight {
    @Id
    private Integer id;

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
    private Integer numberOfBookableSeats;

    @Column
    @NotNull
    private String currency;

    @Column
    @NotNull
    private double totalPrice;

}
