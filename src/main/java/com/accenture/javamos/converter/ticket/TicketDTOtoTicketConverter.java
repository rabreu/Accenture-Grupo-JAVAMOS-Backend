package com.accenture.javamos.converter.ticket;

import com.accenture.javamos.controller.exception.UnauthorizedException;
import com.accenture.javamos.dto.TicketDTO;
import com.accenture.javamos.entity.Ticket;
import com.accenture.javamos.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class TicketDTOtoTicketConverter
  implements Converter<TicketDTO, Ticket> {

  private final SecurityService securityService;

  @Override
  public Ticket convert(TicketDTO source) {
    try {
      return new Ticket(
        source.getId(),
        source.getStatus(),
        securityService.getUserAuthenticated()
      );
    } catch (UnauthorizedException e) {
      return null;
    }
  }
}
