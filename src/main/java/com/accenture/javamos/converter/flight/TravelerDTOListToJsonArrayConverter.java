package com.accenture.javamos.converter.flight;

import com.accenture.javamos.dto.TravelerDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Receive Traveler Data from client and convert to send JsonArray to Amadeus API
 */
@Component
public class TravelerDTOListToJsonArrayConverter
  implements Converter<List<TravelerDTO>, JsonArray> {

  @Override
  public JsonArray convert(List<TravelerDTO> source) {
    return new Gson()
      .toJsonTree(source, new TypeToken<List<TravelerDTO>>() {}.getType())
      .getAsJsonArray();
  }
}
