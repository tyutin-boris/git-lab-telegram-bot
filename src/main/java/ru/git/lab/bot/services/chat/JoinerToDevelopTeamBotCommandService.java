package ru.git.lab.bot.services.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.model.repository.PrivateChatMessageRepository;
import ru.git.lab.bot.model.repository.TgUserRepository;
import ru.git.lab.bot.services.chat.api.BotCommandService;

import java.util.Optional;

;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinerToDevelopTeamBotCommandService implements BotCommandService {

    private final PrivateChatMessageRepository privateChatMessageRepository;
    private final TgUserRepository userRepository;

    @Override
    public Optional<ChatResponse> handle(Message message) {
        Long userId = Optional.ofNullable(message).map(Message::getFrom).map(User::getId)
                .orElseThrow(() -> new RuntimeException("User id not found"));

        boolean userExist = userRepository.existsById(userId);

        if (userExist) {
            ChatResponse response = ChatResponse.builder()
                    .chatId(message.getChatId())
                    .text("Пожалуйста ввидите свой usarname из gitlab")
                    .build();

            return Optional.of(response);

        }
        return Optional.empty();
    }

    @Override
    public BotCommands getHandlingCommand() {
        return BotCommands.JOIN_TO_DEVELOP_TEAM;
    }
}
