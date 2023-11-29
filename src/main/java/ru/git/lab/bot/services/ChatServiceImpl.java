package ru.git.lab.bot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.git.lab.bot.dto.ChatDto;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.mappers.ChatMapper;
import ru.git.lab.bot.model.entities.ChatEntity;
import ru.git.lab.bot.model.repository.ChatRepository;
import ru.git.lab.bot.services.api.ChatService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMapper chatMapper;

    private final ChatRepository chatRepository;

    @Override
    public void save(Chat chat) {
        Chat chatNotNull = Optional.ofNullable(chat)
                .orElseThrow(() -> new RuntimeException("Chat is null"));

        ChatEntity entity = new ChatEntity();
        String title = Optional.ofNullable(chatNotNull.getTitle()).orElse("N/D");

        entity.setId(chatNotNull.getId());
        entity.setType(ChatType.stringToChatType(chatNotNull.getType()));
        entity.setTitle(title);

        chatRepository.save(entity);
    }

    @Override
    public List<ChatDto> getAll() {
        return chatMapper.toDto(chatRepository.findAll());
    }

    @Override
    public ChatDto getById(Long chatId) {
        return chatRepository.findById(chatId)
                .map(chatMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
    }
}
