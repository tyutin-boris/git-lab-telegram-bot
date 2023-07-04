package org.boris.bot.senders;

import org.boris.bot.bot.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MergeRequestSenderImpl implements MergeRequestSender {

    private final TelegramBot telegramBot;

    public MergeRequestSenderImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(String text, Long chatId) {
        telegramBot.sendMessage(text, chatId);
    }
}
