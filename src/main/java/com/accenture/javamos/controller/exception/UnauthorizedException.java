package com.accenture.javamos.controller.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnauthorizedException extends Exception {

  private static final long serialVersionUID = 1L;

  public UnauthorizedException(String message) {
    super(message);
  }
}
