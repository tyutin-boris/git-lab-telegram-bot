package ru.git.lab.bot.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "message_chats")
@NoArgsConstructor
public class MessageChatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "tg_mr_messages_id")
    private Long tgMrMessagesId;

    @Column(name = "tg_id")
    private Integer tgId;

    @Column(name = "chat_id")
    private Long chatId;
}
