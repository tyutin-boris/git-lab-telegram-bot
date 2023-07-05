package org.boris.bot.senders;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MergeRequestSender {
    Message sendMessage(String text, Long chatId);
}
