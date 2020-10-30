package com.accenture.javamos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLikesFlightId implements Serializable {
    @ManyToOne
    private User userId;
    @ManyToOne
    private Flight flightId;
}
