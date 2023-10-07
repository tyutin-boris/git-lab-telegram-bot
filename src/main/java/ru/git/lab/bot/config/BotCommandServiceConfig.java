package ru.git.lab.bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.services.chat.api.BotCommandService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class BotCommandServiceConfig {

    private final List<BotCommandService> botCommandServices;

    @Bean
    public Map<BotCommands, BotCommandService> getCommandServices() {
        return botCommandServices.stream().collect(Collectors.toMap(BotCommandService::getHandlingCommand, Function.identity()));
    }
}
