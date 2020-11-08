package com.accenture.javamos.controller.exception;

public class EmailAlreadyTakenException extends Exception {

  private static final long serialVersionUID = 1L;

  public EmailAlreadyTakenException(String message) {
    super(message);
  }
}
