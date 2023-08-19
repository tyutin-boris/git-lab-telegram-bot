package ru.git.lab.bot.services.api;

import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.model.entities.GitUserEntity;

public interface UserService {
    void saveUserIfNotExist(User user);

    GitUserEntity getByAuthorId(Long authorId);
}
