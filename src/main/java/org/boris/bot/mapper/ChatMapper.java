package org.boris.bot.mapper;

import org.boris.bot.dto.ChatDto;
import org.boris.bot.model.ChatEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper extends ToDtoMapper<ChatEntity, ChatDto> {
}
