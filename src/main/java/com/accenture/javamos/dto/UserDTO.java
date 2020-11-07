package com.accenture.javamos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

  private Integer id;

  @NotBlank
  @Length(min = 3, max = 100)
  private String realName;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Length(min = 6, max = 25)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;
}
