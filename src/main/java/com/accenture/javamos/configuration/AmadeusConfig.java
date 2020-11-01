package com.accenture.javamos.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmadeusConfig {

    @Value("${amadeus.client.id}")
    public String CLIENT_ID;
    @Value("${amadeus.client.secret}")
    public String CLIENT_SECRET;

}
