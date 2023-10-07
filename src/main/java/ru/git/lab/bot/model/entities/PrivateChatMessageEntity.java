package ru.git.lab.bot.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.git.lab.bot.dto.BotCommands;

@Entity
@Setter
@Getter
@Table(name = "private_chat_messages")
@NoArgsConstructor
public class PrivateChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String test;

    @Enumerated(EnumType.STRING)
    private BotCommands botCommands;
}
