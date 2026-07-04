package com.flatmate.backend.chat.controller;

import com.flatmate.backend.chat.dto.ChatRequest;
import com.flatmate.backend.chat.dto.ChatResponse;
import com.flatmate.backend.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{interestId}")
    @PreAuthorize("hasAnyRole('OWNER','TENANT')")
    public List<ChatResponse> history(@PathVariable Long interestId) {

        return chatService.history(interestId);

    }

    @MessageMapping("/send")
    public void send(ChatRequest request) {

        ChatResponse response = chatService.save(request);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + request.getInterestId(),
                response
        );

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER','TENANT')")
    public ChatResponse sendMessage(@RequestBody ChatRequest request) {
        return chatService.save(request);
    }

}