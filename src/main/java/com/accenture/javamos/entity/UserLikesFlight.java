package com.accenture.javamos.entity;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "User_likes_flight")
@Getter
public class UserLikesFlight {
  @EmbeddedId
  @Embedded
  private UserLikesFlightId id;

  public UserLikesFlight(UserLikesFlightId id) {
    this.id = id;
  }

  public UserLikesFlight() {}
}
