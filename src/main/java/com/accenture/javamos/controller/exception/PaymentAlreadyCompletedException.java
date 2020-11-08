package com.accenture.javamos.controller.exception;

public class PaymentAlreadyCompletedException extends Exception {

  private static final long serialVersionUID = 1L;

  public PaymentAlreadyCompletedException(String message) {
    super(message);
  }
}
