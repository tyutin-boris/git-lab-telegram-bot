package org.boris.bot.services;

import org.boris.bot.dto.ChatDto;

import java.util.List;

public interface ChatService {
    List<ChatDto> getAll();

    ChatDto getById(Long chatId);
}
