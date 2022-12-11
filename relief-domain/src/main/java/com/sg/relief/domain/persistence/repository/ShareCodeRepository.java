package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.persistence.entity.ShareCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShareCodeRepository extends JpaRepository<ShareCode, Long> {
    Optional<ShareCode> findByCode(String code);
    Optional<ShareCode> findByUserId(String userId);
}
