package ru.git.lab.bot.services.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.services.bot.api.BotService;
import ru.git.lab.bot.services.chat.member.api.UpdateHandler;

import java.util.List;
import java.util.Optional;

import static ru.git.lab.bot.dto.ChatType.stringToChatType;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final List<UpdateHandler> handlers;

    @Override
    public void handleReceivedUpdate(Update update) {
        Chat chat = Optional.ofNullable(update.getMessage())
                .map(Message::getChat).orElseThrow(
                        () -> new RuntimeException("Chat is null. update id " + update.getUpdateId()));

        ChatType chatType = stringToChatType(chat.getType());

        Optional.ofNullable(update.getMyChatMember()).ifPresent(chatMember -> {
            handlers.forEach(handler -> handler.handle(chatMember));
        });

    }
}
