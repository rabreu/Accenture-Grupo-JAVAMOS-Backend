package com.accenture.javamos.converter.user;

import com.accenture.javamos.entity.Flight;
import com.accenture.javamos.entity.UserLikesFlight;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;

public class UserLikesFlightListToFlightListConverter
  implements Converter<List<UserLikesFlight>, List<Flight>> {

  @Override
  public List<Flight> convert(List<UserLikesFlight> source) {
    return source
      .stream()
      .map(ulf -> ulf.getId().getFlight())
      .collect(Collectors.toList());
  }
}
