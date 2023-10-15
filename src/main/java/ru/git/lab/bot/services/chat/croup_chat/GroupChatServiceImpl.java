package ru.git.lab.bot.services.chat.croup_chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.services.chat.api.ChatService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupChatServiceImpl implements ChatService {

    @Override
    public Optional<ChatResponse> handle(Update update) {
        return Optional.empty();
    }

    @Override
    public ChatType getType() {
        return ChatType.GROUP;
    }
}
