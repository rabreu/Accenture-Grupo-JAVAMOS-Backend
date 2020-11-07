package com.accenture.javamos.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PagarmeConfig {

  @Value("${pagarme.client.id}")
  private String clientId;

  @Value("${pagarme.client.secret}")
  private String clientSecret;
}
