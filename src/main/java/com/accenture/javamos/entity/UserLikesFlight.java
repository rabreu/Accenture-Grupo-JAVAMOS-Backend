package com.accenture.javamos.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    public UserLikesFlight() {
    }
}
