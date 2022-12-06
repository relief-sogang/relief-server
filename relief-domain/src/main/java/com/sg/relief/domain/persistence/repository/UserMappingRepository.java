package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.code.UserMappingStatus;
import com.sg.relief.domain.persistence.entity.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMappingRepository extends JpaRepository<UserMapping, Long> {
    Optional<UserMappingStatus> findByProtegeIdAndGuardianName(String protegeId, String guardianName);
    Optional<UserMappingStatus> findByProtegeIdAndGuardianId(String protegeId, String guardianId);
}
