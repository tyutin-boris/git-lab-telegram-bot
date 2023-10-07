package ru.git.lab.bot.services.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.services.bot.api.BotService;
import ru.git.lab.bot.services.chat.api.ChannelChatService;
import ru.git.lab.bot.services.chat.api.GroupChatService;
import ru.git.lab.bot.services.chat.api.PrivateChatService;
import ru.git.lab.bot.services.chat.api.SupergroupChatService;

import java.util.Optional;

import static ru.git.lab.bot.dto.ChatType.stringToChatType;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final ChannelChatService channelChatService;

    private final PrivateChatService privateChatService;

    private final GroupChatService groupChatService;

    private final SupergroupChatService supergroupChatService;

    @Override
    public Integer handleReceivedUpdate(Update update) {
        log.debug("Update id: " + update.getUpdateId());
        Chat chat = getChat(update);
        ChatType chatType = stringToChatType(chat.getType());

        switch (chatType) {
            case PRIVATE -> privateChatService.handle(update.getMessage());
            case GROUP -> groupChatService.handle();
            case CHANNEL -> channelChatService.handle(update.getMyChatMember());
            case SUPERGROUP -> supergroupChatService.handle();
        }
        return update.getUpdateId();
    }

    private Chat getChat(Update update) {
        Optional<Chat> chatFormMessage = Optional.ofNullable(update.getMessage())
                .map(Message::getChat);

        if (chatFormMessage.isPresent()) {
            log.debug("Get chat from message");
            return chatFormMessage.get();
        }

        Optional<Chat> chatFormMember = Optional.ofNullable(update.getMyChatMember())
                .map(ChatMemberUpdated::getChat);

        if (chatFormMember.isPresent()) {
            log.debug("Get chat from my member");
            return chatFormMember.get();
        } else {
            throw new RuntimeException("Chat not found. Update id " + update.getUpdateId());
        }
    }
}
