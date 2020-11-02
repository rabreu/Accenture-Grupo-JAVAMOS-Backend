package com.accenture.javamos.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Roles")
@Getter
@Setter
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "role")
  @Size(max = 20)
  private String role;
}
