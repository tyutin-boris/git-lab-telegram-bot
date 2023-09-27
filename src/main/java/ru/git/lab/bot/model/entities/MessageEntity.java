package ru.git.lab.bot.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "messages")
@NoArgsConstructor
public class MessageEntity {

    @Id
    UUID id = UUID.randomUUID();

    @Column(name = "message_id")
    Integer messageId;

    @Column(name = "chat_id")
    Long chatId;

    @Column(name = "mr_id")
    Long mrId;

    @Column(name = "author_id")
    Long authorId;

    @Column(name = "create_date_time")
    OffsetDateTime createDateTime;
}
