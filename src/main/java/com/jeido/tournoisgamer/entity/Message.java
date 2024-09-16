package com.jeido.tournoisgamer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Message {
    @Id@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String content;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
