package ru.git.lab.bot.services.chat.api;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;

import java.util.Optional;

public interface BotCommunicationScenariosService {

    default Optional<ChatResponse> handleFirstCommand(Message message) {
        return Optional.empty();
    }

    default Optional<ChatResponse> handleResponse(Message message) {
        return Optional.empty();
    }

    default Optional<ChatResponse> handleCallback(CallbackQuery query) {
        return Optional.empty();
    }

    BotCommands getHandlingCommand();
}
