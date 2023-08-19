package ru.git.lab.bot.services.chat.api;

import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;

public interface ChannelService {

    void handle(ChatMemberUpdated chatMemberUpdated);
}
