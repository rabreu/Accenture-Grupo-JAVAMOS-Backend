package com.accenture.javamos.converter.ticket;

import com.accenture.javamos.controller.exception.UnauthorizedException;
import com.accenture.javamos.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter {

  @Autowired
  private SecurityService securityService;

  @Bean
  public TicketDTOtoTicketConverter toTicket() throws UnauthorizedException {
    return new TicketDTOtoTicketConverter(securityService);
  }
}
