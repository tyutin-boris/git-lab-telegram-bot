package ru.git.lab.bot.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.dto.MergeRequestDto;

@Mapper(componentModel = "spring")
public interface MergeRequestMapper extends ToDtoMapper<MergeRequestEvent, MergeRequestDto>{

    @Override
    @Mapping(source = "objectAttributes.id", target = "mrId")
    MergeRequestDto toDto(MergeRequestEvent event);
}
