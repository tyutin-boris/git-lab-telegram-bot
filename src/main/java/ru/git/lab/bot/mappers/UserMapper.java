package ru.git.lab.bot.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.model.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<UserEntity, User> {

    @Override
    @Mapping(source = "gitId", target = "id")
    User toDto(UserEntity userEntity);

    @Override
    @Mapping(source = "id", target = "gitId")
    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(User user);
}
