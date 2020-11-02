package com.accenture.javamos.repository;

import com.accenture.javamos.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  List<User> findAll();
  Optional<User> findUserByEmail(String email);
}
