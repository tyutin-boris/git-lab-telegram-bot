package ru.git.lab.bot.services.chat.private_chat;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.model.entities.PrivateChatMessageEntity;
import ru.git.lab.bot.model.repository.PrivateChatMessageRepository;
import ru.git.lab.bot.services.chat.api.BotCommunicationScenariosService;
import ru.git.lab.bot.services.chat.api.ChatService;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateChatServiceImpl implements ChatService {

    private final PrivateChatMessageRepository privateChatMessageRepository;
    private final Map<BotCommands, BotCommunicationScenariosService> botCommandServices;

    @Override
    @Transactional
    public Optional<ChatResponse> handle(Update update) {
        Message message = update.getMessage();
        String text = getText(message);

        Optional<BotCommands> botCommand = BotCommands.getCommand(text);
        if (botCommand.isPresent()) {
            log.debug("Bot receive message: " + text);
            return botCommandServices.get(botCommand.get()).handleFirstCommand(message);
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();

        if (Objects.nonNull(callbackQuery)) {
            Optional<Message> callbackQueryMessage = Optional.ofNullable(callbackQuery.getMessage());
            Optional<Long> chatId = callbackQueryMessage.map(Message::getChatId);
            Optional<Long> tgUserId = Optional.ofNullable(callbackQuery.getFrom()).map(User::getId);

            if (chatId.isEmpty() && tgUserId.isEmpty()) {
                return Optional.empty();
            }

            Optional<PrivateChatMessageEntity> privateChatMessage = privateChatMessageRepository
                    .findByTgUserIdAndChatIdOrderByCreateDateDesc(tgUserId.get(), chatId.get())
                    .stream().findFirst();

            if (privateChatMessage.isPresent()) {
                return botCommandServices.get(privateChatMessage.get().getBotCommand()).handleCallback(callbackQuery);
            }
        }

        Long tgUserId = message.getFrom().getId();
        Optional<PrivateChatMessageEntity> privateChatMessage = privateChatMessageRepository
                .findByTgUserIdAndChatIdOrderByCreateDateDesc(tgUserId, message.getChatId())
                .stream().findFirst();

        if (privateChatMessage.isPresent()) {
            log.debug("Bot receive response: " + text);
            return botCommandServices.get(privateChatMessage.get().getBotCommand()).handleResponse(message);
        }

        return Optional.empty();
    }

    @Override
    public ChatType getType() {
        return ChatType.PRIVATE;
    }

    private String getText(Message message) {
        return Optional.ofNullable(message).map(Message::getText).orElse(StringUtils.EMPTY);
    }
}
