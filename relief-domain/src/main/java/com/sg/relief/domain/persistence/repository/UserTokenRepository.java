package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.persistence.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByUserId(Long userId);
    List<UserToken> findAllByUserId(Long userId);
}
