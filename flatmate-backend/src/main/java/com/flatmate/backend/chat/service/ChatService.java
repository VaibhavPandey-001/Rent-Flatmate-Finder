package com.flatmate.backend.chat.service;

import com.flatmate.backend.chat.dto.ChatRequest;
import com.flatmate.backend.chat.dto.ChatResponse;
import com.flatmate.backend.chat.entity.ChatMessage;
import com.flatmate.backend.chat.repository.ChatRepository;
import com.flatmate.backend.exception.BadRequestException;
import com.flatmate.backend.exception.ResourceNotFoundException;
import com.flatmate.backend.interest.entity.Interest;
import com.flatmate.backend.interest.entity.InterestStatus;
import com.flatmate.backend.interest.repository.InterestRepository;
import com.flatmate.backend.user.User;
import com.flatmate.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final InterestRepository interestRepository;
    private final UserRepository userRepository;

    public ChatResponse save(ChatRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Interest interest = interestRepository.findById(request.getInterestId())
                .orElseThrow(() -> new ResourceNotFoundException("Interest not found"));

        if (interest.getStatus() != InterestStatus.ACCEPTED) {
            throw new BadRequestException("Chat is not available");
        }

        boolean allowed =
                interest.getTenant().getId().equals(sender.getId()) ||
                        interest.getListing().getOwner().getId().equals(sender.getId());

        if (!allowed) {
            throw new BadRequestException("Access denied");
        }

        ChatMessage chat = ChatMessage.builder()
                .interest(interest)
                .sender(sender)
                .message(request.getMessage())
                .build();

        chat = chatRepository.save(chat);

        return ChatResponse.builder()
                .id(chat.getId())
                .sender(sender.getName())
                .message(chat.getMessage())
                .sentAt(chat.getSentAt())
                .build();

    }

    public List<ChatResponse> history(Long interestId) {

        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new ResourceNotFoundException("Interest not found"));

        return chatRepository.findByInterestOrderBySentAtAsc(interest)
                .stream()
                .map(chat -> ChatResponse.builder()
                        .id(chat.getId())
                        .sender(chat.getSender().getName())
                        .message(chat.getMessage())
                        .sentAt(chat.getSentAt())
                        .build())
                .toList();

    }

}