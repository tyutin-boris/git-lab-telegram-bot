package ru.git.lab.bot.services.bot;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator;
import ru.git.lab.bot.model.entities.ChatEntity;
import ru.git.lab.bot.model.repository.ChatRepository;

import javax.persistence.EntityManager;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.git.lab.bot.dto.ChatType.stringToChatType;

@ActiveProfiles("dev")
@SpringBootTest
class TelegramBotTest {

    private final int updateId = 1;

    private final OffsetDateTime mockTime = OffsetDateTime.of(1, 1, 1, 1, 1, 1, 1, ZoneOffset.ofHours(3));

    @Autowired
    private TelegramBot bot;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldThrowExceptionWhenUpdateIsNull() {
        //given
        Update update = null;

        //when
        Executable executable = () -> bot.onUpdateReceived(update);

        //then
        RuntimeException runtimeException = assertThrows(RuntimeException.class, executable);
        assertEquals("Update is null", runtimeException.getMessage());
    }

    @Test
    @Ignore
    void shouldSaveChatWhenChatTypeIsChannelAndMemberStatusIsAdministrator() {
        //given
        long catId = 1L;
        String chatType = "channel";
        String chatTitle = "title";
        //TODO fix static mock
        mockOffsetDateTime();

        Chat chat = getChat(catId, chatType, chatTitle);
        ChatMemberUpdated chatMemberUpdated = getChatMemberUpdated(chat, new ChatMemberAdministrator());
        Update update = getUpdate(chatMemberUpdated);
        ChatEntity expectedChat = getExpectedChat(catId, chatType, chatTitle);

        //when
        bot.onUpdateReceived(update);
        flashAndClear();

        //then
        Optional<ChatEntity> actualChat = chatRepository.findById(catId);
        assertEquals(expectedChat, actualChat);
    }

    private ChatEntity getExpectedChat(long catId, String chatType, String chatTitle) {
        ChatEntity chatEntity = new ChatEntity();

        chatEntity.setId(catId);
        chatEntity.setTitle(chatTitle);
        chatEntity.setType(stringToChatType(chatType));
        chatEntity.setCreateDate(mockTime);

        return chatEntity;
    }

    private ChatMemberUpdated getChatMemberUpdated(Chat chat, ChatMember newChatMember) {
        ChatMemberUpdated chatMemberUpdated = new ChatMemberUpdated();

        chatMemberUpdated.setChat(chat);
        chatMemberUpdated.setNewChatMember(newChatMember);

        return chatMemberUpdated;
    }

    private Chat getChat(long catId, String chatType, String chatTitle) {
        Chat chat = new Chat();

        chat.setId(catId);
        chat.setType(chatType);
        chat.setTitle(chatTitle);

        return chat;
    }

    private Update getUpdate(ChatMemberUpdated chatMemberUpdated) {
        Update update = new Update();

        update.setUpdateId(updateId);
        update.setMyChatMember(chatMemberUpdated);

        return update;
    }

    private void flashAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    private void mockOffsetDateTime() {
        try (MockedStatic<OffsetDateTime> utilities = Mockito.mockStatic(OffsetDateTime.class)) {
            utilities.when(OffsetDateTime::now).thenReturn(mockTime);
        }
    }
}
