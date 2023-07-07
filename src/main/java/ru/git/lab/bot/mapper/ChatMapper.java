package ru.git.lab.bot.mapper;

import ru.git.lab.bot.dto.ChatDto;
import ru.git.lab.bot.model.entities.ChatEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper extends ToDtoMapper<ChatEntity, ChatDto> {
}
