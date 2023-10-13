package ru.git.lab.bot.services.chat;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.dto.JoinToDeveloperTeamStage;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.entities.PrivateChatMessageEntity;
import ru.git.lab.bot.model.repository.GitUserRepository;
import ru.git.lab.bot.model.repository.PrivateChatMessageRepository;
import ru.git.lab.bot.model.repository.TgUserRepository;
import ru.git.lab.bot.services.api.GitUserService;
import ru.git.lab.bot.services.chat.api.BotCommandService;

import java.time.OffsetDateTime;
import java.util.Optional;

;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinerToDevelopTeamBotCommandService implements BotCommandService {

    private final TgUserRepository userRepository;
    private final PrivateChatMessageRepository privateChatMessageRepository;

    private final GitUserRepository gitUserRepository;

    @Override
    @Transactional
    public Optional<ChatResponse> handle(Message message) {
        if (message == null) {
            return Optional.empty();
        }

        String text = getText(message);

        if (StringUtils.isBlank(text)) {
            return Optional.empty();
        }

        if (text.startsWith("/")) {
            Long userId = Optional.ofNullable(message.getFrom()).map(User::getId)
                    .orElseThrow(() -> new RuntimeException("User id not found"));

            boolean userExist = userRepository.existsById(userId);

            if (userExist) {
                ChatResponse response = ChatResponse.builder()
                        .chatId(message.getChatId())
                        .text("Пожалуйста ввидите свой usarname из gitlab")
                        .build();

                PrivateChatMessageEntity entity = new PrivateChatMessageEntity();
                entity.setChatId(message.getChatId());
                entity.setTgUserId(userId);
                entity.setBotCommand(getHandlingCommand());
                entity.setCreateDate(OffsetDateTime.now());
                entity.setStageStep(JoinToDeveloperTeamStage.REQUEST_USERNAME.getStep());
                privateChatMessageRepository.save(entity);

                return Optional.of(response);
            }
        } else {

            Optional<PrivateChatMessageEntity> entity = privateChatMessageRepository.findFirstByChatIdAndTgUserIdByDesc(message.getChatId(), message.getFrom().getId());

            if (entity.isPresent() && BotCommands.JOIN_TO_DEVELOP_TEAM.equals(entity.get().getBotCommand())) {
                Integer stageStep = entity.get().getStageStep();
                if (JoinToDeveloperTeamStage.RECEIVE_USERNAME.getStep() == stageStep) {

                    PrivateChatMessageEntity newEntity = new PrivateChatMessageEntity();
                    newEntity.setChatId(message.getChatId());
                    newEntity.setTgUserId(message.getFrom().getId());
                    newEntity.setBotCommand(BotCommands.JOIN_TO_DEVELOP_TEAM);
                    newEntity.setCreateDate(OffsetDateTime.now());
                    newEntity.setStageStep(JoinToDeveloperTeamStage.RECEIVE_USERNAME.getStep());
                    newEntity.setText(message.getText());
                    privateChatMessageRepository.save(newEntity);

                    Optional<GitUserEntity> gitUserEntity = gitUserRepository.findByUsername(message.getText());
                    if (gitUserEntity.isPresent()) {
                        GitUserEntity gitUserEntity1 = gitUserEntity.get();
                        gitUserEntity1.setTgId(message.getFrom().getId());
                        gitUserRepository.save(gitUserEntity1);
                    }
                }
            }
            ChatResponse value = ChatResponse.builder()
                    .chatId(message.getChatId())
                    .text("Username успешно сохранен")
                    .build();
            return Optional.of(value);
        }

        return Optional.empty();
    }

    @Override
    public BotCommands getHandlingCommand() {
        return BotCommands.JOIN_TO_DEVELOP_TEAM;
    }

    private String getText(Message message) {
        return Optional.ofNullable(message.getText()).orElse(StringUtils.EMPTY);
    }
}
