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
@Table(name = "tg_mr_messages")
@NoArgsConstructor
public class TgMrMessageEntity {

    @Id
    @Column(nullable = false)
    private Long id;

    @Column(name = "tg_id")
    private Integer tgId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "mr_id", nullable = false)
    private Long mrId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name ="text")
    private String text;

    @Column(name ="is_draft")
    private boolean isDraft;

    @Column(name = "create_date_time", nullable = false)
    private OffsetDateTime createDateTime;
}
