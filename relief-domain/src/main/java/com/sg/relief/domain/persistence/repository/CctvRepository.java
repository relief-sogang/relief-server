package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.persistence.entity.Cctv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CctvRepository extends JpaRepository<Cctv, Long> {
}
