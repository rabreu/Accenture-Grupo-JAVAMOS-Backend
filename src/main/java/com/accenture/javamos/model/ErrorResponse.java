package com.accenture.javamos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
  private final int statusValue;
  private final String message;
}
