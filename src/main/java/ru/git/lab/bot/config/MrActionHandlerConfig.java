package ru.git.lab.bot.config;

import lombok.RequiredArgsConstructor;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.services.handlers.mr.MrActionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class MrActionHandlerConfig {

    private final List<MrActionHandler> actionHandlers;

    @Bean
    public Map<Action, MrActionHandler> actionHandlers() {
        return actionHandlers.stream()
                .collect(Collectors.toMap(MrActionHandler::getAction, Function.identity()));
    }
}
