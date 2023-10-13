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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tg_user_id")
    private Long tgUserId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    private BotCommands botCommand;

    @Column(name = "stage_stap")
    private Integer stageStep;

    @Column(name = "create_date")
    private OffsetDateTime createDate;
}
