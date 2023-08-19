package ru.git.lab.bot.services.chat.member.api;

import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;

public interface UpdateHandler {

    void handle(ChatMemberUpdated chatMemberUpdated);
}
