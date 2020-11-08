package com.accenture.javamos.converter.flight;

import com.accenture.javamos.dto.FlightOrderDTO;
import com.accenture.javamos.entity.Flight;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightOrder;
import java.util.List;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Receive FlightOrder from Amadeus and convert it to FlightOrderDTO (to send to client)
 */
@Component
@AllArgsConstructor
public class FlightOrderToFlightOrderDTOConverter
  implements Converter<FlightOrder, FlightOrderDTO> {

  private final FlightConverter flightConverter;
  private final ModelMapper mapper;

  @Override
  public FlightOrderDTO convert(FlightOrder source) {
    mapper.addConverter(
      new org.modelmapper.Converter<FlightOfferSearch[], Flight>() {
        @Override
        public Flight convert(
          MappingContext<FlightOfferSearch[], Flight> context
        ) {
          List<Flight> flights = flightConverter
            .toFlightList()
            .convert(context.getSource());
          return flights.get(0);
        }
      }
    );

    return mapper.map(source, FlightOrderDTO.class);
  }
}
