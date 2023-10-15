package ru.git.lab.bot.services.chat.private_chat.scenarios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.dto.KeyboardButton;
import ru.git.lab.bot.model.entities.ChatEntity;
import ru.git.lab.bot.model.entities.PrivateChatMessageEntity;
import ru.git.lab.bot.model.repository.ChatRepository;
import ru.git.lab.bot.model.repository.ChatsTgGitUsersRepository;
import ru.git.lab.bot.model.repository.PrivateChatMessageRepository;
import ru.git.lab.bot.model.repository.TgUserRepository;
import ru.git.lab.bot.services.chat.api.BotCommunicationScenariosService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.git.lab.bot.dto.BotCommands.ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS;
import static ru.git.lab.bot.services.chat.private_chat.scenarios.PrivateChatScenariosTask.RECEIVE_CHAT_NAME;
import static ru.git.lab.bot.services.chat.private_chat.scenarios.PrivateChatScenariosTask.REQUEST_CHAT_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddChatToReceiveGitLabNotification implements BotCommunicationScenariosService {

    private final TgUserRepository tgUserRepository;

    private final ChatRepository chatRepository;
    private final ChatsTgGitUsersRepository chatsTgGitUsersRepository;
    private final PrivateChatMessageRepository privateChatMessageRepository;

    @Override
    public Optional<ChatResponse> handleFirstCommand(Message message) {
        if (message == null) {
            return Optional.empty();
        }

        Long tgUserId = Optional.ofNullable(message.getFrom()).map(User::getId)
                .orElseThrow(() -> new RuntimeException("User id not found"));

        boolean userExist = tgUserRepository.existsById(tgUserId);

        if (userExist) {

            PrivateChatMessageEntity entity = new PrivateChatMessageEntity();
            Integer taskNumber = getHandlingCommand().getTaskNumber().get(REQUEST_CHAT_NAME);

            entity.setChatId(message.getChatId());
            entity.setTgUserId(tgUserId);
            entity.setBotCommand(getHandlingCommand());
            entity.setScenariosTaskNumber(taskNumber);
            entity.setCreateDate(OffsetDateTime.now());

            privateChatMessageRepository.save(entity);

            List<ChatEntity> chats = chatRepository.findAllByType(ChatType.CHANNEL);
            List<KeyboardButton> buttons = chats.stream().map(chat -> {
                KeyboardButton keyboardButton = new KeyboardButton();
                keyboardButton.setText(chat.getTitle());
                keyboardButton.setCallbackData(chat.getId().toString());
                return keyboardButton;
            }).toList();

            ChatResponse response = ChatResponse.builder()
                    .chatId(message.getChatId())
                    .text("Пожалуйста ввидите название чата для которого вы хотите получать уведомления от GitLab")
                    .buttons(buttons)
                    .build();

            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ChatResponse> handleCallback(CallbackQuery query) {
        Message message = query.getMessage();
        Long chatId = message.getChatId();
        Long tgUserId = message.getFrom().getId();

        Optional<PrivateChatMessageEntity> privateChatMessageOpt = privateChatMessageRepository
                .findByTgUserIdAndChatIdOrderByCreateDateDesc(tgUserId, chatId);

        if (privateChatMessageOpt.isEmpty()) {
            return Optional.empty();
        }

        PrivateChatMessageEntity previousMessage = privateChatMessageOpt.get();
        if (Objects.equals(previousMessage.getScenariosTaskNumber(), REQUEST_CHAT_NAME.getNumber())) {

            PrivateChatMessageEntity entity = new PrivateChatMessageEntity();
            entity.setChatId(chatId);
            entity.setTgUserId(tgUserId);
            entity.setBotCommand(getHandlingCommand());
            entity.setScenariosTaskNumber(RECEIVE_CHAT_NAME.getNumber());
            entity.setCreateDate(OffsetDateTime.now());

            privateChatMessageRepository.save(entity);

//            Optional<ChatEntity> chatForNotification = chatRepository.findByName("name");

//            chatsTgGitUsersRepository.findAllByTgId()
        }

        return BotCommunicationScenariosService.super.handleCallback(query);
    }

    @Override
    public BotCommands getHandlingCommand() {
        return ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS;
    }
}
