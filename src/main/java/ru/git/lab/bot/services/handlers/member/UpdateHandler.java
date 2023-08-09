package ru.git.lab.bot.services.handlers.member;

import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;

public interface UpdateHandler {

    void handle(ChatMemberUpdated chatMemberUpdated);
}
