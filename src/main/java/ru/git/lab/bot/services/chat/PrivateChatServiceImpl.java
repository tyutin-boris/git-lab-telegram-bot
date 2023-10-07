package ru.git.lab.bot.services.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.services.api.TgUserService;
import ru.git.lab.bot.services.chat.api.BotCommandService;
import ru.git.lab.bot.services.chat.api.PrivateChatService;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateChatServiceImpl implements PrivateChatService {

    private final Map<BotCommands, BotCommandService> botCommandServices;

    @Override
    public void handle(Message message) {
        checkNotNull(message);
        String text = getText(message);

        BotCommands botCommand = getBotCommands(text);

        if (text.startsWith("/")) {
            botCommandServices.get(botCommand).handle(message);
        }
    }

    private String getText(Message message) {
        return Optional.ofNullable(message.getText()).orElse(StringUtils.EMPTY);
    }

    private BotCommands getBotCommands(String text) {
        return BotCommands.getCommand(text).orElseThrow(() -> new RuntimeException("This command not present: " + text));
    }

    private static void checkNotNull(Message message) {
        Optional.ofNullable(message).orElseThrow(() -> new RuntimeException("Message from private chat is null"));
    }
}
