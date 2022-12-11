package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.code.UserMappingStatus;
import com.sg.relief.domain.persistence.entity.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMappingRepository extends JpaRepository<UserMapping, Long> {
    Optional<UserMapping> findByProtegeIdAndGuardianName(String protegeId, String guardianName);
    Optional<UserMapping> findByProtegeIdAndGuardianId(String protegeId, String guardianId);
    List<UserMapping> findAllByProtegeId(String protegeId);
    List<UserMapping> findALlByGuardianId(String guardianId);
}
