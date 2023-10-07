package ru.git.lab.bot.services.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.services.api.TgUserService;
import ru.git.lab.bot.services.chat.api.PrivateChatService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateChatServiceImpl implements PrivateChatService {

    private final TgUserService tgUserService;

    @Override
    public void handle(Message message) {
        checkNotNull(message);

        tgUserService.save(message.getFrom());
    }

    private static void checkNotNull(Message message) {
        Optional.ofNullable(message).orElseThrow(() -> new RuntimeException("Message from private chat is null"));
    }
}
