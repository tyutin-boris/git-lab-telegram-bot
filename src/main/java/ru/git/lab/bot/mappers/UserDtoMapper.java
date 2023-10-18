package ru.git.lab.bot.mappers;

import org.mapstruct.Mapper;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserDtoMapper extends ToDtoMapper<User, UserDto> {
}
