package com.accenture.javamos.converter;

import com.accenture.javamos.service.AmadeusService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IataCodeConverter implements Converter<String[], String> {
  @Autowired
  private AmadeusService amadeusService;

  @Override
  public String convert(String[] iataCodes) {
    List<String> arrIataCodes = Arrays.asList(iataCodes);

    return Arrays
      .asList(amadeusService.getAirlines())
      .stream()
      .filter(a -> arrIataCodes.contains(a.getIataCode()))
      .findFirst()
      .get()
      .getCommonName();
  }
}
