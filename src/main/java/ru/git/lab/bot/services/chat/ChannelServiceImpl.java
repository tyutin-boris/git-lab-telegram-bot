package ru.git.lab.bot.services.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import ru.git.lab.bot.dto.ChatMemberStatus;
import ru.git.lab.bot.model.entities.ChatEntity;
import ru.git.lab.bot.model.repository.ChatRepository;
import ru.git.lab.bot.services.chat.api.ChannelService;

import java.time.OffsetDateTime;
import java.util.Optional;

import static ru.git.lab.bot.dto.ChatType.stringToChatType;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChatRepository chatRepository;

    @Override
    public void handle(ChatMemberUpdated chatMemberUpdated) {
        Chat channel = chatMemberUpdated.getChat();
        Long channelId = channel.getId();
        String channelTitle = channel.getTitle();
        log.debug(String.format("Received message from channel with name %s and id %s.", channelTitle, channelId));

        ChatMemberStatus memberStatus = Optional.ofNullable(chatMemberUpdated.getNewChatMember())
                .map(ChatMember::getStatus)
                .map(ChatMemberStatus::stringToStatus)
                .orElseThrow(() -> new RuntimeException("Failed to get member status"));

        switch (memberStatus) {
            case ADMINISTRATOR:
                chatRepository.save(createChatEntity(channel));
                log.debug(String.format("Bot was add to channel with name %s and id %s, as admin.", channelTitle, channelId));
            case KICKED:
                chatRepository.findById(channelId).ifPresent(chatRepository::delete);
                log.debug(String.format("Bot was kicked from channel with name %s with id %s.", channelTitle, channelId));
        }
    }

    private ChatEntity createChatEntity(Chat chat) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(chat.getId());
        chatEntity.setTitle(chat.getTitle());
        chatEntity.setType(stringToChatType(chat.getType()));
        chatEntity.setCreateDate(OffsetDateTime.now());
        return chatEntity;
    }
}