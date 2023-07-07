package ru.git.lab.bot.services.handlers.member;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {

    void handle(Update update);
}
