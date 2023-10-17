package ru.git.lab.bot.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "chats_tg_git_users")
@NoArgsConstructor
public class ChatsTgGitUsersEntity {

    @Id
    @Column(nullable = false)
    private Long tgId;

    private Long gitId;

    private Long chatId;
}
