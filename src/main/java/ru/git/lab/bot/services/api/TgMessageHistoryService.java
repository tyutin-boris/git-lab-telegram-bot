package ru.git.lab.bot.services.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TgMessageHistoryService {

    void save(Update update);
}
