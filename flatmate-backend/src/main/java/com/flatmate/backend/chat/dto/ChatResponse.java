package com.flatmate.backend.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatResponse {

    private Long id;

    private String sender;

    private String message;

    private LocalDateTime sentAt;

}