package com.accenture.javamos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TravelerDTO {

  private String id;
  private String dateOfBirth;
  private NameDTO name;
  private ContactDTO contact;
  private List<DocumentDTO> documents = null;
}
