package org.boris.bot.services.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMemberUpdateHandler implements UpdateHandler {

    @Override
    public void handle(Update update) {
        Optional<ChatMemberUpdated> myChatMember = Optional.ofNullable(update.getMyChatMember());
        Optional<Chat> optionalChat = myChatMember.map(ChatMemberUpdated::getChat);
        Optional<ChatMember> optionalNewChatMember = myChatMember.map(ChatMemberUpdated::getNewChatMember);

        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();

            Long chatId = chat.getId();
            String chatTitle = chat.getTitle();
            String chatType = chat.getType();

            log.debug(String.format("Handle message from %s %s with id %s.", chatType, chatTitle, chatId));

            String newMemberStatus = optionalNewChatMember.map(ChatMember::getStatus)
                    .orElse("");

            if (ChatMemberStatus.KICKED.toString().equalsIgnoreCase(newMemberStatus)) {
                log.debug(String.format("Boot was kicked from %s %s with id %s.", chatType, chatTitle, chatId));

            } else if (ChatMemberStatus.ADMINISTRATOR.toString().equalsIgnoreCase(newMemberStatus)) {
                log.debug(String.format("Boot was add to %s %s with id %s.", chatType, chatTitle, chatId));
            }
        }
    }
}
