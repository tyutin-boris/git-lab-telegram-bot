package ru.git.lab.bot.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    @Column(name = "author_email")
    String authorEmail;

    @Column(name = "author_username")
    String authorUsername;

    @Column(name = "create_date_time")
    OffsetDateTime createDateTime;
}
