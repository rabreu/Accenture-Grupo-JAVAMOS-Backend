package com.accenture.javamos.converter.user;

import com.accenture.javamos.dto.UserDTO;
import com.accenture.javamos.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDTOConverter implements Converter<User, UserDTO> {

  @Autowired
  private ModelMapper mapper;

  public UserDTO convert(User source) throws IllegalArgumentException {
    return mapper.map(source, UserDTO.class);
  }
}
