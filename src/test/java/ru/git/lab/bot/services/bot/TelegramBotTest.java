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

//    @Test
//    void shouldThrowExceptionWhenUpdateIsNull() {
//        //given
//        Update update = null;
//
//        //when
//        Executable executable = () -> bot.onUpdateReceived(update);
//
//        //then
//        RuntimeException runtimeException = assertThrows(RuntimeException.class, executable);
//        assertEquals("Update is null", runtimeException.getMessage());
//    }
}
