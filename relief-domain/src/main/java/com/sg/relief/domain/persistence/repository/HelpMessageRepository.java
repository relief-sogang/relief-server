package com.sg.relief.domain.persistence.repository;

import com.sg.relief.domain.persistence.entity.HelpMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HelpMessageRepository extends JpaRepository<HelpMessage, Long> {
    List <HelpMessage> findAllBySenderId (String senderId);
    List <HelpMessage> findAllByReceiverId (String receiverId);
}
