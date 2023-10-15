package ru.git.lab.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.Arrays;
import java.util.List;

import static ru.git.lab.bot.dto.BotCommands.ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS;
import static ru.git.lab.bot.dto.BotCommands.ADD_GIT_USERNAME;

@Configuration
public class BotCommandConfig {

    @Bean
    public List<BotCommand> botCommands() {
        return Arrays.asList(
                new BotCommand(ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS.getCommand(),
                        ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS.getDescription()),
                new BotCommand(ADD_GIT_USERNAME.getCommand(), ADD_GIT_USERNAME.getDescription()));
    }
}
