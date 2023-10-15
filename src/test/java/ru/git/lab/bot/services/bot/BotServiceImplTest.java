package ru.git.lab.bot.services.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.meta.api.objects.*;
import ru.git.lab.bot.dto.ChatResponse;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.model.entities.ChatsTgGitUsersEntity;
import ru.git.lab.bot.model.entities.PrivateChatMessageEntity;
import ru.git.lab.bot.model.entities.TgUserEntity;
import ru.git.lab.bot.model.repository.ChatsTgGitUsersRepository;
import ru.git.lab.bot.model.repository.PrivateChatMessageRepository;
import ru.git.lab.bot.model.repository.TgUserRepository;
import ru.git.lab.bot.services.api.TgUserService;
import ru.git.lab.bot.services.bot.api.BotService;
import ru.git.lab.bot.services.chat.private_chat.scenarios.PrivateChatScenariosTask;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static ru.git.lab.bot.dto.BotCommands.ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS;
import static ru.git.lab.bot.dto.BotCommands.START;

@SpringBootTest
@ActiveProfiles("test")
public class BotServiceImplTest {

    private final int updateId = 1;
    private final Long tgUserId = 586815794L;
    private final Long chatId = 1L;

    @SpyBean
    private TgUserService tgUserService;

    @Autowired
    private BotService sut;

    @Autowired
    private ChatsTgGitUsersRepository chatsTgGitUsersRepository;

    @Autowired
    private PrivateChatMessageRepository privateChatMessageRepository;

    @Autowired
    private TgUserRepository tgUserRepository;

    @BeforeEach
    private void setUp() {
        TgUserEntity entity = new TgUserEntity();
        entity.setId(tgUserId);
        entity.setUsername("username");
        tgUserRepository.save(entity);
    }


//    @Test
//    public void shouldThrowExceptionWhenChatFromMessageIsNull() {
////        given
//        Message message = new Message();
//        message.setChat(null);
//
//        Update update = getUpdate(message);
//
////        when
//        Executable executable = () -> sut.handleReceivedUpdate(update);
//
////        then
//        RuntimeException runtimeException = assertThrows(RuntimeException.class, executable);
//        assertEquals("Chat not found. Update id " + updateId, runtimeException.getMessage());
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenChatFromMyChatMemberInIsNull() {
//        given
//        ChatMemberUpdated chatMemberUpdated = new ChatMemberUpdated();
//        chatMemberUpdated.setChat(null);
//
//        Update update = getUpdate(chatMemberUpdated);
//
//        when
//        Executable executable = () -> sut.handleReceivedUpdate(update);
//
//        then
//        RuntimeException runtimeException = assertThrows(RuntimeException.class, executable);
//        assertEquals("Chat not found. Update id " + updateId, runtimeException.getMessage());
//    }
//
//    @Test
//    public void shouldSaveNewUserWhenStartChat() {
//        given
//        User user = getUser();
//        Update update = getUpdate(user);
//
//        when
//        sut.handleReceivedUpdate(update);
//
//        then
//        verify(tgUserService).saveUserIfNotExist(eq(user));
//    }
//
//    @Test
//    public void shouldSendRequestChatNameWhenAddChatForReceivedNotification() {
//        given
//        String expectedText = "Пожалуйста ввидите название чата для которого вы хотите получать уведомления от GitLab";
//
//        User user = getUser();
//        Message message = getMessageForPrivateChat(user, ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS.getCommand());
//        Update update = getUpdate(user, message);
//
//        when
//        Optional<ChatResponse> actualChatResponse = sut.handleReceivedUpdate(update);
//
//        then
//        checkPrivateMessage(chatId, tgUserId);
//
//        assertTrue(actualChatResponse.isPresent());
//        ChatResponse actualResponse = actualChatResponse.get();
//        assertEquals(chatId, actualResponse.getChatId());
//        assertEquals(expectedText, actualResponse.getText());
//    }
//
//    private Update getUpdate(User user) {
//        Chat chat = new Chat();
//        chat.setType(ChatType.PRIVATE.getName());
//
//        Message message = new Message();
//        message.setChat(chat);
//        message.setFrom(user);
//        message.setText(START.getCommand());
//
//        Update update = new Update();
//        update.setMessage(message);
//        return update;
//    }
//
//    private Update getUpdate(User user, Message message) {
//        Update update = new Update();
//        update.setMessage(message);
//        return update;
//    }
//
//    private Message getMessageForPrivateChat(User user, String text) {
//        Chat chat = new Chat();
//        chat.setId(chatId);
//        chat.setType(ChatType.PRIVATE.getName());
//
//        Message message = new Message();
//        message.setChat(chat);
//        message.setFrom(user);
//        message.setText(text);
//        return message;
//    }
//
//    private User getUser() {
//        User from = new User();
//        from.setId(tgUserId);
//        from.setFirstName("Boris");
//        from.setIsBot(false);
//        from.setLastName("Tyutin");
//        from.setUserName("btyutin");
//        return from;
//    }
//
//    private Update getUpdate(ChatMemberUpdated chatMemberUpdated) {
//        Update update = new Update();
//
//        update.setUpdateId(updateId);
//        update.setMyChatMember(chatMemberUpdated);
//
//        return update;
//    }
//
//    private Update getUpdate(Message message) {
//        Update update = new Update();
//
//        update.setUpdateId(updateId);
//        update.setMessage(message);
//
//        return update;
//    }
//
//    private void checkChatsTgGitUsers(Long chatId, Long tgId, Long gitId, User user) {
//        List<ChatsTgGitUsersEntity> chatsRgGitUsers = chatsTgGitUsersRepository.findAllByTgId(user.getId());
//        assertFalse(chatsRgGitUsers.isEmpty());
//
//        ChatsTgGitUsersEntity actual = chatsRgGitUsers.stream().findFirst().orElse(null);
//        assertNotNull(actual);
//        assertEquals(chatId, actual.getChatId());
//        assertEquals(tgId, actual.getTgId());
//        assertEquals(gitId, actual.getGitId());
//    }
//
//    private void checkPrivateMessage(Long chatId, Long tgUserId) {
//        List<PrivateChatMessageEntity> privateChatMessage = privateChatMessageRepository.findByTgUserId(tgUserId);
//        assertEquals(1, privateChatMessage.size());
//
//        PrivateChatMessageEntity actual = privateChatMessage.stream().findFirst().orElse(null);
//        assertNotNull(actual);
//        assertEquals(tgUserId, actual.getTgUserId());
//        assertEquals(chatId, actual.getChatId());
//        assertEquals(ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS, actual.getBotCommand());
//        assertEquals(PrivateChatScenariosTask.REQUEST_CHAT_NAME.getNumber(), actual.getScenariosTaskNumber());
//        assertNotNull(actual.getCreateDate());
//    }
}
