package ru.git.lab.bot.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.git.lab.bot.dto.BotCommands;

import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
@Table(name = "private_chat_messages")
@NoArgsConstructor
public class PrivateChatMessageEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tg_user_id", nullable = false)
    private Long tgUserId;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BotCommands botCommand;

    @Column(name = "stage_stap", nullable = false)
    private Integer scenariosTaskNumber;

    @Column(name = "create_date", nullable = false)
    private OffsetDateTime createDate;
}
