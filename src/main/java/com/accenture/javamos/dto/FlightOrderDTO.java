package com.accenture.javamos.dto;

import com.accenture.javamos.entity.Flight;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class FlightOrderDTO {

  private Flight flight;
  private List<TravelerDTO> travelers = null;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
