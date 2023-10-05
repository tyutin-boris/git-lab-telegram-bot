package ru.git.lab.bot.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BotCommandEnum {

    ADD_GIT_PROJECT("/add_me_to_git_lab_project", "add you to git project group");

    private final String command;
    private final String description;
}
