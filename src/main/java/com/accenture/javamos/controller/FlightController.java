package com.accenture.javamos.controller;

import com.accenture.javamos.controller.exception.UnauthorizedException;
import com.accenture.javamos.converter.flight.FlightConverter;
import com.accenture.javamos.dto.FlightOrderDTO;
import com.accenture.javamos.dto.TicketDTO;
import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.entity.Ticket;
import com.accenture.javamos.model.ErrorResponse;
import com.accenture.javamos.model.FlightOfferSearchRequest;
import com.accenture.javamos.model.FlightOfferSearchResponse;
import com.accenture.javamos.service.FlightService;
import com.accenture.javamos.service.TicketService;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOrder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight")
public class FlightController {

  @Autowired
  FlightService flightService;

  @Autowired
  private FlightConverter flightConverter;

  @Autowired
  TicketService ticketService;

  /**
   * Search flight offers
   * @param search
   * @return A List<Fligth> wrapped in a FlightOfferSearchResponse attribute 'flights'
   * @throws ResponseException
   */
  @GetMapping(
    path = "/search",
    params = { "from", "to", "departure", "adults" }
  )
  public ResponseEntity<?> flightOfferSearchWithNoReturnDate(
    FlightOfferSearchRequest search
  )
    throws ResponseException {
    try {
      List<Flight> flights = flightService.flightOfferSearchWithNoReturnDate(
        search
      );

      FlightOfferSearchResponse response = new FlightOfferSearchResponse(
        flights
      );

      return new ResponseEntity<FlightOfferSearchResponse>(
        response,
        HttpStatus.OK
      );
    } catch (ResponseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(
      new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
      ),
      HttpStatus.INTERNAL_SERVER_ERROR
    );
  }

  // TODO: separate pricing from booking confirm
  /**
   * Receive an order to book a flight
   * @param orderDTO
   * @return The reservation id, the reservation status (pending or completed) and the OrderDTO with Flight and List<Travelers> info all wrapped in a TicketDAO
   * @throws UnauthorizedException when user is not authenticated
   */
  @PostMapping(
    path = "/order",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public ResponseEntity<?> orderFlight(@RequestBody FlightOrderDTO orderDTO)
    throws UnauthorizedException {
    // book an order
    FlightOrder order = flightService.createOrder(orderDTO);
    // create a ticket
    Ticket ticket = ticketService.create(order);

    // update orderDTO with consumed data from service
    orderDTO = flightConverter.toFlightOrderDTO().convert(order);

    // create a ticket to send to client
    TicketDTO ticketDTO = new TicketDTO(
      ticket.getId(),
      ticket.getStatus(),
      orderDTO
    );
    return new ResponseEntity<>(ticketDTO, HttpStatus.CREATED);
  }

  @GetMapping(path = "/order", params = { "id" })
  public ResponseEntity<?> getFlightOrder(String id) {
    return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
  }
}
