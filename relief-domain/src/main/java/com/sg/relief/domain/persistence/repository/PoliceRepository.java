package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.persistence.entity.Police;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliceRepository extends JpaRepository <Police, Long> {
}
