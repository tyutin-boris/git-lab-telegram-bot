package ru.git.lab.bot.services.chat.private_chat.scenarios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.BotCommands;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.services.api.TgUserService;
import ru.git.lab.bot.services.chat.api.BotCommunicationScenariosService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StartBotCommunicationScenariosService implements BotCommunicationScenariosService {

    private final TgUserService tgUserService;

    @Override
    public Optional<ChatResponse> handleFirstCommand(Message message) {
        tgUserService.saveUserIfNotExist(message.getFrom());

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setChatId(message.getChatId());
        chatResponse.setText("Вас категорически приветствует GitLab бот. " +
                "Я умею сообщать о MR. " +
                "Отправляю сообщения о ваших MR  ах, только в указанные вами чаты. " +
                "Скоро научусь хранить историю MR ов. " +
                "И сообщать о статусе запущенного вами pipeline.");

        return Optional.of(chatResponse);
    }

    @Override
    public BotCommands getHandlingCommand() {
        return BotCommands.START;
    }
}
