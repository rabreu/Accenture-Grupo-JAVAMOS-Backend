package com.accenture.javamos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtResponse {
  private final String jwttoken;
  private final String authenticatedUserName;
}
