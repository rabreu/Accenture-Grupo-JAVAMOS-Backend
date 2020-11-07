package com.accenture.javamos.converter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

  @Autowired
  public UserDTOToEntityConverter toEntity;

  @Autowired
  public UserEntityToDTOConverter toDTO;
}
