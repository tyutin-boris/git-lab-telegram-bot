package ru.git.lab.bot.services.impl;

import lombok.RequiredArgsConstructor;
import ru.git.lab.bot.dto.ChatDto;
import ru.git.lab.bot.mapper.ChatMapper;
import ru.git.lab.bot.model.repository.ChatRepository;
import ru.git.lab.bot.services.ChatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Override
    public List<ChatDto> getAll() {
        return chatMapper.toDto(chatRepository.findAll());
    }

    @Override
    public ChatDto getById(Long chatId) {
        return chatRepository.findById(chatId).map(chatMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
    }
}
