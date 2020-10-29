package com.accenture.javamos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class FlightWithReturnDateResponse {
    private final String originDepartureLocationCode;
    private final String destinationDepartureLocationCode;
    private final String originReturnLocationCode;
    private final String destinationReturnLocationCode;
    private final Date departureDate;
    private final Date returnDate;
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
