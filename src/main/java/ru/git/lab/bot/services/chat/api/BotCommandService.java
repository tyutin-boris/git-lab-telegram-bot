package ru.git.lab.bot.services.chat.api;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.BotCommands;

public interface BotCommandService {

    void handle(Message message);

    BotCommands getHandlingCommand();
}
