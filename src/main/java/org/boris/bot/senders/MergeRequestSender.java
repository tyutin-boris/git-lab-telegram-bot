package org.boris.bot.senders;

public interface MergeRequestSender {
    void sendMessage(String text, String chatId);
}
