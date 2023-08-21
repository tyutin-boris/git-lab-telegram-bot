package ru.git.lab.bot.services.bot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("dev")
@SpringBootTest
class TelegramBotTest {

    private final int updateId = 1;

    @Autowired
    private TelegramBot bot;

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
    void shouldThrowExceptionWhenChatFromMessageIsNull() {
        //given
        Message message = new Message();
        message.setChat(null);

        Update update = getUpdate(message);

        //when
        Executable executable = () -> bot.onUpdateReceived(update);

        //then
        RuntimeException runtimeException = assertThrows(RuntimeException.class, executable);
        assertEquals("Chat not found. Update id " + updateId, runtimeException.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenChatFromMyChatMemberInIsNull() {
        //given
        ChatMemberUpdated chatMemberUpdated = new ChatMemberUpdated();
        chatMemberUpdated.setChat(null);

        Update update = getUpdate(chatMemberUpdated);

        //when
        Executable executable = () -> bot.onUpdateReceived(update);

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
