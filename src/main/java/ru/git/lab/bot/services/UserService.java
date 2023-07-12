package ru.git.lab.bot.services;

import ru.git.lab.bot.api.mr.User;

public interface UserService {
    void saveUserIfNotExist(User user);
}
