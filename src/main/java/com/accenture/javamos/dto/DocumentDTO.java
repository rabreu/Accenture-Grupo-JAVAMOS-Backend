package com.accenture.javamos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentDTO {

  private String documentType;
  private String number;
  private String validityCountry;
  private String issuanceCountry;
  private String nationality;
  private String birthPlace;
  private String issuanceLocation;
  private String issuanceDate;
  private String expiryDate;
  private Boolean holder;
}
