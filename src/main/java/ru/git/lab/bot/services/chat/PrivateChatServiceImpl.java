package ru.git.lab.bot.services.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.services.chat.api.BotCommandService;
import ru.git.lab.bot.services.chat.api.ChatService;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateChatServiceImpl implements ChatService {

    private final Map<BotCommands, BotCommandService> botCommandServices;

    @Override
    public Optional<ChatResponse> handle(Update update) {
        Message message = update.getMessage();
        checkNotNull(message);

        String text = getText(message);
        BotCommands botCommand = getBotCommands(text);

        if (text.startsWith("/")) {
            return botCommandServices.get(botCommand).handle(message);
        }
        return Optional.empty();
    }

    @Override
    public ChatType getType() {
        return ChatType.PRIVATE;
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
