package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.persistence.entity.ShareCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareCodeRepository extends JpaRepository<ShareCode, Long> {
    Optional<ShareCode> findByCode(String code);
    Optional<ShareCode> findByUserId(String userId);
    List<ShareCode> findAllByUserId(String userId);
}
