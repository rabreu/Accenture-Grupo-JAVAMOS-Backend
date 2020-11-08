package com.accenture.javamos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDTO {

  private TicketDTO ticket;
  private Map<String, Object> customer;
  private Map<String, Object> billing;
  private Map<String, Object> card;

  @JsonProperty(value = "card_number")
  private String cardNumber;

  @JsonProperty(value = "card_holder_name")
  private String cardHolderName;

  @JsonProperty(value = "card_cvv")
  private String cardCvv;

  @JsonProperty(value = "card_expiration_date")
  private String cardExpirationDate;
}
