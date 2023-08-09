package ru.git.lab.bot.services.handlers.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import ru.git.lab.bot.model.entities.ChatEntity;
import ru.git.lab.bot.model.repository.ChatRepository;

import java.time.OffsetDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMemberUpdateHandler implements UpdateHandler {

    private final ChatRepository chatRepository;

    @Override
    public void handle(ChatMemberUpdated chatMemberUpdated) {
        Chat chat = chatMemberUpdated.getChat();
        ChatMember newChatMember = chatMemberUpdated.getNewChatMember();

        Long chatId = chat.getId();
        String chatTitle = chat.getTitle();
        String chatType = chat.getType();

        log.debug(String.format("Handle message from %s %s with id %s.", chatType, chatTitle, chatId));

        String newMemberStatus = newChatMember.getStatus();

        if (ChatMemberStatus.KICKED.toString().equalsIgnoreCase(newMemberStatus)) {
            chatRepository.findById(chatId).ifPresent(chatRepository::delete);
            log.debug(String.format("Bot was kicked from %s %s with id %s.", chatType, chatTitle, chatId));
        } else if (ChatMemberStatus.ADMINISTRATOR.toString().equalsIgnoreCase(newMemberStatus)) {
            chatRepository.save(createChatEntity(chat));
            log.debug(String.format("Bot was add to %s %s with id %s.", chatType, chatTitle, chatId));
        }
    }

    private ChatEntity createChatEntity(Chat chat) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(chat.getId());
        chatEntity.setTitle(chat.getTitle());
        chatEntity.setType(chat.getType());
        chatEntity.setCreateDate(OffsetDateTime.now());
        return chatEntity;
    }
}
