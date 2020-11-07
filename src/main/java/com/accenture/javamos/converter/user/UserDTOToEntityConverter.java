package com.accenture.javamos.converter.user;

import com.accenture.javamos.dto.UserDTO;
import com.accenture.javamos.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDTOToEntityConverter implements Converter<UserDTO, User> {

  @Autowired
  private ModelMapper mapper;

  @Override
  public User convert(UserDTO source) {
    return mapper.map(source, User.class);
  }
}
