package ru.git.lab.bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.services.chat.api.BotCommunicationScenariosService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class BotCommandServiceConfig {

    private final List<BotCommunicationScenariosService> botCommunicationScenariosServices;

    @Bean
    public Map<BotCommands, BotCommunicationScenariosService> getCommandServices() {
        return botCommunicationScenariosServices.stream()
                .collect(Collectors.toMap(BotCommunicationScenariosService::getHandlingCommand, Function.identity()));
    }
}
