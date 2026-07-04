package com.flatmate.backend.chat.entity;

import com.flatmate.backend.interest.entity.Interest;
import com.flatmate.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Interest interest;

    @ManyToOne
    private User sender;

    @Column(nullable = false, length = 1000)
    private String message;

    private LocalDateTime sentAt;

    @PrePersist
    public void onCreate() {
        sentAt = LocalDateTime.now();
    }

}