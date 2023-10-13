package ru.git.lab.bot.services.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.model.entities.PrivateChatMessageEntity;
import ru.git.lab.bot.model.repository.PrivateChatMessageRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageHandlerImpl implements ChatMessageHandler {

    private final PrivateChatMessageRepository privateChatMessageRepository;

    @Override
    public Optional<ChatResponse> handle(Message message) {
        Long chatId = message.getChatId();
        Long id = message.getFrom().getId();

        List<PrivateChatMessageEntity> privateMessages = privateChatMessageRepository.findByChatIdAndTgUserId(chatId, id);

        return Optional.empty();
    }
}
