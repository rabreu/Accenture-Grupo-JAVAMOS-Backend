package com.accenture.javamos.repository;

import com.accenture.javamos.entity.UserLikesFlight;
import com.accenture.javamos.entity.UserLikesFlightId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikesFlightRepository
  extends JpaRepository<UserLikesFlight, UserLikesFlightId> {
  List<UserLikesFlight> findAll();

  @Query(
    value = "SELECT * FROM USER_LIKES_FLIGHT " + "WHERE FLIGHT_ID = :Id",
    nativeQuery = true
  )
  Optional<UserLikesFlight> findByFlightId(@Param("Id") Integer Id);
}
