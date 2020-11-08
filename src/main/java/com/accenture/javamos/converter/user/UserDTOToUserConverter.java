package com.accenture.javamos.converter.user;

import com.accenture.javamos.dto.UserDTO;
import com.accenture.javamos.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDTOToUserConverter implements Converter<UserDTO, User> {

  private final ModelMapper mapper;

  @Override
  public User convert(UserDTO source) {
    return mapper.map(source, User.class);
  }
}
