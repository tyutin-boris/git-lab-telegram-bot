package ru.git.lab.bot.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tg_git_user")
@NoArgsConstructor
@IdClass(TgGitUserId.class)
public class TgGitUsersEntity {

    @Id
    @Column(nullable = false)
    private Long tgId;

    @Id
    @Column(nullable = false)
    private Long gitId;
}
