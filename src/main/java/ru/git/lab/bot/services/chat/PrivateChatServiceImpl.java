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
import ru.git.lab.bot.dto.JoinToDeveloperTeamStage;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.entities.PrivateChatMessageEntity;
import ru.git.lab.bot.model.repository.GitUserChatRepository;
import ru.git.lab.bot.model.repository.PrivateChatMessageRepository;
import ru.git.lab.bot.services.chat.api.BotCommandService;
import ru.git.lab.bot.services.chat.api.ChatService;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateChatServiceImpl implements ChatService {

    private final Map<BotCommands, BotCommandService> botCommandServices;

    private final PrivateChatMessageRepository privateChatMessageRepository;
    private  final GitUserChatRepository gitUserChatRepository;


    @Override
    public Optional<ChatResponse> handle(Update update) {
        Message message = update.getMessage();
        checkNotNull(message);

        String text = getText(message);
        BotCommands botCommand = getBotCommands(text);

        if (text.startsWith("/")) {
            log.debug("Private service received command: " + botCommand.getCommand());
            return botCommandServices.get(botCommand).handle(message);
        }

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

                Optional<GitUserEntity> gitUserEntity = gitUserChatRepository.findByUsername(message.getText());
                if(gitUserEntity.isPresent()) {
                    GitUserEntity gitUserEntity1 = gitUserEntity.get();
                    //TODO Изменитьт на добавлление юсер наме.
//                    gitUserEntity1.
                }
            }
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
