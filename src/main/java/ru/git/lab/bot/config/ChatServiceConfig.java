package ru.git.lab.bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.services.chat.api.ChatService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class ChatServiceConfig {

    private final List<ChatService> chatServices;

    @Bean
    public Map<ChatType, ChatService> getChatServices() {
        return chatServices.stream().collect(Collectors.toMap(ChatService::getType, Function.identity()));
    }
}
