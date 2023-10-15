package ru.git.lab.bot.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.model.entities.TgUserEntity;

@Mapper(componentModel = "spring")
public interface TgUserMapper extends ToEntityMapper<User, TgUserEntity> {

    @Mapping(source = "userName", target = "username")
    @Override
    TgUserEntity toEntity(User user);
}
