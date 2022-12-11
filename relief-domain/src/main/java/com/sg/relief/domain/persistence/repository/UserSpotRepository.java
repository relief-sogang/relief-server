package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.persistence.entity.UserSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSpotRepository extends JpaRepository<UserSpot, Long> {
    List<UserSpot> findAllByUserId(String userId);
    Optional<UserSpot> findByUserIdAndSpotName(String userId, String spotName);
}
