package ru.git.lab.bot.services.chat.private_chat.scenarios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.model.entities.PrivateChatMessageEntity;
import ru.git.lab.bot.model.repository.PrivateChatMessageRepository;
import ru.git.lab.bot.model.repository.TgUserRepository;
import ru.git.lab.bot.services.chat.api.BotCommunicationScenariosService;

import java.time.OffsetDateTime;
import java.util.Optional;

import static ru.git.lab.bot.dto.BotCommands.ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS;
import static ru.git.lab.bot.services.chat.private_chat.scenarios.PrivateChatScenariosTask.REQUEST_CHAT_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddChatToReceiveGitLabNotification implements BotCommunicationScenariosService {

    private final TgUserRepository tgUserRepository;

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
            ChatResponse response = ChatResponse.builder()
                    .chatId(message.getChatId())
                    .text("Пожалуйста ввидите название чата для которого вы хотите получать уведомления от GitLab")
                    .build();

            PrivateChatMessageEntity entity = new PrivateChatMessageEntity();
            Integer taskNumber = getHandlingCommand().getTaskNumber().get(REQUEST_CHAT_NAME);

            entity.setChatId(message.getChatId());
            entity.setTgUserId(tgUserId);
            entity.setBotCommand(getHandlingCommand());
            entity.setScenariosTaskNumber(taskNumber);
            entity.setCreateDate(OffsetDateTime.now());

            privateChatMessageRepository.save(entity);

            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ChatResponse> handleResponse(Message message) {
        return Optional.empty();
    }

    @Override
    public BotCommands getHandlingCommand() {
        return ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS;
    }
}
