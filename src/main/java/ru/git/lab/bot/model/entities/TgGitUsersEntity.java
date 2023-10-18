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
@Table(name = "tg_git_user")
@NoArgsConstructor
public class TgGitUsersEntity {

    @Id
    @Column(nullable = false)
    private Long tgId;

    private Long gitId;

    private String gitUsername;
}
