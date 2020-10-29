package com.accenture.javamos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class FlightWithNoReturnDateResponse {
    private final String originLocationCode;
    private final String destinationLocationCode;
    private final Date departureDate;
    private final List<String> airlineNames;
    private final boolean oneWay;
    private final int numberOfBookableSeats;
    private final String currency;
    private final double totalPrice;

    public String getdepartureDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy às HH:mm");
        return format.format(this.departureDate);
    }
}
