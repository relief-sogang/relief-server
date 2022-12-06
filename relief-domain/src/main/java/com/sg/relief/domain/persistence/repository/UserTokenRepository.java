package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.persistence.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
}
