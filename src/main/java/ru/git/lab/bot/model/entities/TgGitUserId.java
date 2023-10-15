package ru.git.lab.bot.model.entities;

import lombok.*;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TgGitUserId implements Serializable {

    private Long tgId;

    private Long gitId;

    private Long chatId;
}
