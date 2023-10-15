package ru.git.lab.bot.services.chat.api;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.dto.ChatType;

import java.util.Optional;

public interface ChatService {

    Optional<ChatResponse> handle(Update update);

    ChatType getType();
}
