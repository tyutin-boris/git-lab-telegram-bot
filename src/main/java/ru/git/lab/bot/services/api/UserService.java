package ru.git.lab.bot.services.api;

import ru.git.lab.bot.dto.UserDto;
import ru.git.lab.bot.model.entities.GitUserEntity;

public interface UserService {

    void saveUserIfNotExist(UserDto user);

    GitUserEntity getByAuthorId(Long authorId);
}
