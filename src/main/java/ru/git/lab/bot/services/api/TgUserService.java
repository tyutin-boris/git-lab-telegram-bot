package ru.git.lab.bot.services.api;

import org.telegram.telegrambots.meta.api.objects.User;

public interface TgUserService {

    void save(User user);
}
