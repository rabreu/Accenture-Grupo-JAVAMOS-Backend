package com.accenture.javamos.converter.user;

import com.accenture.javamos.dto.UserDTO;
import com.accenture.javamos.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserToUserDTOConverter implements Converter<User, UserDTO> {

  private final ModelMapper mapper;

  public UserDTO convert(User source) throws IllegalArgumentException {
    return mapper.map(source, UserDTO.class);
  }
}
