package com.flatmate.backend.chat.repository;

import com.flatmate.backend.chat.entity.ChatMessage;
import com.flatmate.backend.interest.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByInterestOrderBySentAtAsc(Interest interest);

}