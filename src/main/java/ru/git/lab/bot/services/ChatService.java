package ru.git.lab.bot.services;

import ru.git.lab.bot.dto.ChatDto;

import java.util.List;

public interface ChatService {
    List<ChatDto> getAll();

    ChatDto getById(Long chatId);

    List<Long> getAllChatId();
}
