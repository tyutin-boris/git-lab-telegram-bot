package ru.git.lab.bot.services.chat.private_chat.scenarios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.entities.PrivateChatMessageEntity;
import ru.git.lab.bot.model.entities.TgGitUsersEntity;
import ru.git.lab.bot.model.repository.GitUserRepository;
import ru.git.lab.bot.model.repository.PrivateChatMessageRepository;
import ru.git.lab.bot.model.repository.TgGitUsersRepository;
import ru.git.lab.bot.model.repository.TgUserRepository;
import ru.git.lab.bot.services.chat.api.BotCommunicationScenariosService;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

import static ru.git.lab.bot.services.chat.private_chat.scenarios.PrivateChatScenariosTask.RECEIVE_USERNAME;
import static ru.git.lab.bot.services.chat.private_chat.scenarios.PrivateChatScenariosTask.REQUEST_USERNAME;

;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddGitUsernameScenariosService implements BotCommunicationScenariosService {

    private final TgUserRepository tgUserRepository;
    private final GitUserRepository gitUserRepository;
    private final TgGitUsersRepository tgGitUsersRepository;
    private final PrivateChatMessageRepository privateChatMessageRepository;

    @Override
    public Optional<ChatResponse> handleFirstCommand(Message message) {
        if (message == null) {
            return Optional.empty();
        }

        String text = getText(message);

        if (StringUtils.isBlank(text)) {
            return Optional.empty();
        }

        Long userId = Optional.ofNullable(message.getFrom()).map(User::getId)
                .orElseThrow(() -> new RuntimeException("User id not found"));

        boolean userExist = tgUserRepository.existsById(userId);

        if (userExist) {

            PrivateChatMessageEntity entity = new PrivateChatMessageEntity();
            entity.setChatId(message.getChatId());
            entity.setTgUserId(userId);
            entity.setBotCommand(getHandlingCommand());
            entity.setCreateDate(OffsetDateTime.now());
            entity.setScenariosTaskNumber(REQUEST_USERNAME.getNumber());

            privateChatMessageRepository.save(entity);

            ChatResponse response = ChatResponse.builder()
                    .chatId(message.getChatId())
                    .text("Пожалуйста введите свой usarname для gitlab")
                    .build();

            return Optional.of(response);
        }

        return Optional.empty();
    }

    @Override
    public Optional<ChatResponse> handleResponse(Message message) {
        Optional<PrivateChatMessageEntity> privateMessage = privateChatMessageRepository
                .findByTgUserIdAndChatIdOrderByCreateDateDesc(message.getChatId(), message.getFrom().getId())
                .stream()
                .findFirst();

        if (privateMessage.isPresent() && BotCommands.ADD_GIT_USERNAME.equals(privateMessage.get().getBotCommand())) {
            if (Objects.equals(REQUEST_USERNAME.getNumber(), privateMessage.get().getScenariosTaskNumber())) {
                String text = message.getText();
                Optional<GitUserEntity> gitUser = gitUserRepository.findByUsername(text);

                if (gitUser.isPresent()) {
                    TgGitUsersEntity tgGitUsers = new TgGitUsersEntity();
                    tgGitUsers.setGitId(gitUser.get().getId());
                    tgGitUsers.setTgId(message.getFrom().getId());

                    tgGitUsersRepository.save(tgGitUsers);

                    PrivateChatMessageEntity newEntity = new PrivateChatMessageEntity();

                    newEntity.setChatId(message.getChatId());
                    newEntity.setTgUserId(message.getFrom().getId());
                    newEntity.setBotCommand(BotCommands.ADD_GIT_USERNAME);
                    newEntity.setCreateDate(OffsetDateTime.now());
                    newEntity.setScenariosTaskNumber(RECEIVE_USERNAME.getNumber());
                    newEntity.setText(text);

                    privateChatMessageRepository.save(newEntity);
                } else {
                    ChatResponse value = ChatResponse.builder()
                            .chatId(message.getChatId())
                            .text("Пользователя с таким username: " + text + " не найден.")
                            .build();

                    return Optional.of(value);
                }
            }
        }
        ChatResponse value = ChatResponse.builder()
                .chatId(message.getChatId())
                .text("Username успешно сохранен")
                .build();
        return Optional.of(value);
    }

    @Override
    public BotCommands getHandlingCommand() {
        return BotCommands.ADD_GIT_USERNAME;
    }

    private String getText(Message message) {
        return Optional.ofNullable(message.getText()).orElse(StringUtils.EMPTY);
    }
}
