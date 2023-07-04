package org.boris.bot.senders;

public interface MergeRequestSender {
    void sendMessage(String text, Long chatId);
}
