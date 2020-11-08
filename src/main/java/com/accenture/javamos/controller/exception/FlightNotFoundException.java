package com.accenture.javamos.controller.exception;

public class FlightNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  public FlightNotFoundException(String message) {
    super(message);
  }
}
