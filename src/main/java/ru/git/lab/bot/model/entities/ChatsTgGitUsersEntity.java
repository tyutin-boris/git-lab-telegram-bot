package ru.git.lab.bot.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "chats_tg_git_users")
@NoArgsConstructor
@IdClass(TgGitUserId.class)
public class ChatsTgGitUsersEntity {

    @Id
    @Column(nullable = false)
    private Long tgId;

    @Id
    @Column(nullable = false)
    private Long gitId;

    @Column(name = "chat_id")
    private Long chatId;
}
