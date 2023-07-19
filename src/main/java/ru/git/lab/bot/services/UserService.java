package ru.git.lab.bot.services;

import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.model.entities.UserEntity;

public interface UserService {
    void saveUserIfNotExist(User user);

    UserEntity getByAuthorId(Long authorId);
}
