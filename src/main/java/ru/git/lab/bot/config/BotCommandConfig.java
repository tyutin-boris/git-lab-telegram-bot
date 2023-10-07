package ru.git.lab.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.Arrays;
import java.util.List;

import static ru.git.lab.bot.dto.BotCommands.JOIN_TO_DEVELOP_TEAM;

@Configuration
public class BotCommandConfig {

    @Bean
    public List<BotCommand> botCommands() {
        //TODO try refactor to abstract factory
        return Arrays.asList(
                //                new BotCommand("/start", "hello bot"),
                //                new BotCommand("/add_user", "add new user"),
                //                new BotCommand("/delete_user", "delete user"),
                new BotCommand(JOIN_TO_DEVELOP_TEAM.getCommand(), JOIN_TO_DEVELOP_TEAM.getDescription()));
    }
}
