package com.accenture.javamos.converter.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

  @Autowired
  private ModelMapper mapper;

  @Bean
  public UserDTOToUserConverter toEntity() {
    return new UserDTOToUserConverter(mapper);
  }

  @Bean
  public UserToUserDTOConverter toDTO() {
    return new UserToUserDTOConverter(mapper);
  }

  @Bean
  public UserLikesFlightListToFlightListConverter toFlightsLikedList() {
    return new UserLikesFlightListToFlightListConverter();
  }
}
