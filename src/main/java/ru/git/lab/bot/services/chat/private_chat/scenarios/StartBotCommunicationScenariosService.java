package ru.git.lab.bot.services.chat.private_chat.scenarios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.services.api.TgUserService;
import ru.git.lab.bot.services.chat.api.BotCommunicationScenariosService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StartBotCommunicationScenariosService implements BotCommunicationScenariosService {

    private final TgUserService tgUserService;

    @Override
    public Optional<ChatResponse> handleFirstCommand(Message message) {
        tgUserService.saveUserIfNotExist(message.getFrom());
        return Optional.empty();
    }

    @Override
    public Optional<ChatResponse> handleResponse(Message message) {
        return Optional.empty();
    }

    @Override
    public BotCommands getHandlingCommand() {
        return BotCommands.START;
    }
}
