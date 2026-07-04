package com.flatmate.backend.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {

    private Long interestId;

    private String message;

}