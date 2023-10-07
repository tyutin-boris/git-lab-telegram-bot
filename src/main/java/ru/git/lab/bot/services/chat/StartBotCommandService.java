package ru.git.lab.bot.services.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.services.api.TgUserService;
import ru.git.lab.bot.services.chat.api.BotCommandService;

@Slf4j
@Service
@RequiredArgsConstructor
public class StartBotCommandService implements BotCommandService {

    private final TgUserService tgUserService;

    @Override
    public void handle(Message message) {
        tgUserService.saveUserIfNotExist(message.getFrom());
    }

    @Override
    public BotCommands getHandlingCommand() {
        return BotCommands.START;
    }
}
