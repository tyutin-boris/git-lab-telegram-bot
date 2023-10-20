package ru.git.lab.bot.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "tg_mr_messages")
@NoArgsConstructor
public class TgMrMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(name = "mr_id", nullable = false)
    private Long mrId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "text")
    private String text;

    @Column(name = "is_draft")
    private boolean isDraft;

    @Column(name = "is_delete")
    private boolean isDelete;

    @Column(name = "create_date_time", nullable = false)
    private OffsetDateTime createDateTime;

    @OneToMany
    @JoinColumn(name = "tg_mr_messages_id")
    private Set<MessageChatsEntity> chats;
}
