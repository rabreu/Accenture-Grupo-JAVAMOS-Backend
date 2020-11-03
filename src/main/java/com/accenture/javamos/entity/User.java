package com.accenture.javamos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String realName;

  @Column(unique = true)
  @NotNull
  private String email;

  @JsonIgnore
  @Column
  private String password;

  @Column
  private Boolean active;

  @JsonIgnore
  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
    name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles;
}
