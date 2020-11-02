package com.accenture.javamos.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class AmadeusConfig {
  @Value("${amadeus.client.id}")
  private String clientId;

  @Value("${amadeus.client.secret}")
  private String clientSecret;
}
