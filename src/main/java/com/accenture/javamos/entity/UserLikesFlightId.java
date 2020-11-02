package com.accenture.javamos.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLikesFlightId implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @ManyToOne
  private User userId;

  @ManyToOne
  private Flight flightId;
}
