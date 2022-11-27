package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.persistence.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
    Optional<Sample> findById(Long id);
}
