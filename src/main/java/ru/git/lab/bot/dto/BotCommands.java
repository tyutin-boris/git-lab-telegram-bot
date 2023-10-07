package ru.git.lab.bot.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum BotCommands {

    START("/start", "first command"),
    ADD_GIT_PROJECT("/add_me_to_git_lab_project", "add you to git project group");

    private final String command;
    private final String description;

    public static Optional<BotCommands> getCommand(String text) {
        for (BotCommands command : BotCommands.values()) {
            if (command.getCommand().equals(text)) {
                return Optional.of(command);
            }
        }
        return Optional.empty();
    }
}
