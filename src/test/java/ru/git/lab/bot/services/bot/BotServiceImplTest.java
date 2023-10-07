package ru.git.lab.bot.services.bot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.telegram.telegrambots.meta.api.objects.*;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.services.api.TgUserService;
import ru.git.lab.bot.services.bot.api.BotService;
import ru.git.lab.bot.services.chat.api.ChannelService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

@SpringJUnitConfig(classes = BotServiceImpl.class)
public class BotServiceImplTest {

    private final int updateId = 1;

    @MockBean
    private ChannelService channelService;

    @MockBean
    private TgUserService tgUserService;

    @Autowired
    private BotService sut;

    @Test
    public void shouldThrowExceptionWhenChatFromMessageIsNull() {
        //given
        Message message = new Message();
        message.setChat(null);

        Update update = getUpdate(message);

        //when
        Executable executable = () -> sut.handleReceivedUpdate(update);

        //then
        RuntimeException runtimeException = assertThrows(RuntimeException.class, executable);
        assertEquals("Chat not found. Update id " + updateId, runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenChatFromMyChatMemberInIsNull() {
        //given
        ChatMemberUpdated chatMemberUpdated = new ChatMemberUpdated();
        chatMemberUpdated.setChat(null);

        Update update = getUpdate(chatMemberUpdated);

        //when
        Executable executable = () -> sut.handleReceivedUpdate(update);

        //then
        RuntimeException runtimeException = assertThrows(RuntimeException.class, executable);
        assertEquals("Chat not found. Update id " + updateId, runtimeException.getMessage());
    }

    @Test
    public void shouldSaveNewUserWhenStartChat() {
        //given
        User user = getUser();
        Update update = getUpdate(user);

        //when
        sut.handleReceivedUpdate(update);

        // then
        Mockito.verify(tgUserService).save(eq(user));
    }

    private Update getUpdate(User user) {
        Chat chat = new Chat();
        chat.setType(ChatType.PRIVATE.getName());

        Message message = new Message();
        message.setChat(chat);
        message.setFrom(user);

        Update update = new Update();
        update.setMessage(message);
        return update;
    }

    private User getUser() {
        User from = new User();
        from.setId(586815794L);
        from.setFirstName("Boris");
        from.setIsBot(false);
        from.setLastName("Tyutin");
        from.setUserName("btyutin");
        return from;
    }

    private Update getUpdate(ChatMemberUpdated chatMemberUpdated) {
        Update update = new Update();

        update.setUpdateId(updateId);
        update.setMyChatMember(chatMemberUpdated);

        return update;
    }

    private Update getUpdate(Message message) {
        Update update = new Update();

        update.setUpdateId(updateId);
        update.setMessage(message);

        return update;
    }
}
