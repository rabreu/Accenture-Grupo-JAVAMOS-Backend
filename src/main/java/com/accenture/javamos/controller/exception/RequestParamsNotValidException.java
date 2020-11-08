package com.accenture.javamos.controller.exception;

public class RequestParamsNotValidException extends Exception {

  private static final long serialVersionUID = 1L;

  public RequestParamsNotValidException(String message) {
    super(message);
  }
}
