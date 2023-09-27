package ru.git.lab.bot.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.git.lab.bot.dto.ChatType;

import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
@Table(name = "chats")
@NoArgsConstructor
public class ChatEntity {

    @Id
    private Long id;

    @Column(name = "type")
    private ChatType type;

    @Column(name = "title")
    private String title;

    @Column(name = "create_date")
    private OffsetDateTime createDate;
}


