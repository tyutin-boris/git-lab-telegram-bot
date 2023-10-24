package ru.git.lab.bot.services.api;

import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.git.lab.bot.dto.ChatDto;

import java.util.List;

public interface ChatService {

    void save(Chat chat);

    List<ChatDto> getAll();

    ChatDto getById(Long chatId);

}
