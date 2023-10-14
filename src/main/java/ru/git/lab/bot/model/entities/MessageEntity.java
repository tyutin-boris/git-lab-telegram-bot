package ru.git.lab.bot.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
@Table(name = "messages")
@NoArgsConstructor
public class MessageEntity {

    @Id
    @Column(nullable = false)
    Long id;

    @Column(name = "chat_id", nullable = false)
    Long chatId;

    @Column(name = "mr_id", nullable = false)
    Long mrId;

    @Column(name = "author_id", nullable = false)
    Long authorId;

    @Column(name = "create_date_time", nullable = false)
    OffsetDateTime createDateTime;
}
