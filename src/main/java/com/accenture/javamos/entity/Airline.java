package com.accenture.javamos.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "airline")
@Getter
public class Airline {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String logo_url;
}
