package ru.git.lab.bot.services.bot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.git.lab.bot.services.bot.api.BotService;
import ru.git.lab.bot.services.chat.api.ChannelService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = BotServiceImpl.class)
public class BotServiceImplTest {

    private final int updateId = 1;

    @MockBean
    private ChannelService channelService;

    @Autowired
    private BotService botService;

    @Test
    public void shouldThrowExceptionWhenChatFromMessageIsNull() {
        //given
        Message message = new Message();
        message.setChat(null);

        Update update = getUpdate(message);

        //when
        Executable executable = () -> botService.handleReceivedUpdate(update);

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
        Executable executable = () -> botService.handleReceivedUpdate(update);

        //then
        RuntimeException runtimeException = assertThrows(RuntimeException.class, executable);
        assertEquals("Chat not found. Update id " + updateId, runtimeException.getMessage());
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