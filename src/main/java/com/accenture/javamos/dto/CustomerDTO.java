package com.accenture.javamos.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO {

  private String externalId;
  private String name;
  private String type;
  private String country;
  private String email;
  private List<PaymentDocumentDTO> documents;
  private List<PaymentPhoneDTO> phone_numbers;
}
