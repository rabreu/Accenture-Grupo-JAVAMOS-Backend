package com.accenture.javamos.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "User_likes_flight")
@Getter
public class UserLikesFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @ManyToOne
    private Flight flight;

    @Setter
    @ManyToOne
    private User user;
}
