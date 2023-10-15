package ru.git.lab.bot.services.chat.api;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;

import java.util.Optional;

public interface BotCommunicationScenariosService {

    Optional<ChatResponse> handleFirstCommand(Message message);
    Optional<ChatResponse> handleResponse(Message message);

    BotCommands getHandlingCommand();
}