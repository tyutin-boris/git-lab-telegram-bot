package org.boris.bot.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boris.bot.config.BotConfig;
import org.boris.bot.services.handlers.UpdateHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final List<UpdateHandler> handlers;

    @Override
    public void onUpdateReceived(Update update) {

        if (CollectionUtils.isEmpty(handlers)) {
            log.warn("Update handlers not found");
            return;
        }

        log.info("Start handling update event");
        handlers.forEach(handler -> handler.handle(update));

//        chatId.add(update.getMyChatMember()
//                           .getChat()
//                           .getId());
//        String text = Optional.ofNullable(update.getMessage())
//                .map(Message::getText)
//                .orElse("Сообщения нет");
//        long chatId = Optional.ofNullable(update.getMessage())
//                .map(Message::getChatId)
//                .orElse(0L);
//
//        System.out.println(chatId + "new");
//        switch (text) {
//            case "/start":
////                sendMessage("Привет человек из чата " + chatId);
//                break;
//            case "/love":
////                sendMessage("Я счастлив, что бог дал мне глаза, и я смог разглядеть тебя в толпе. И благодарен ему за возможность называть тебя своей половинкой.");
//        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onRegister() {
        log.info("Boot was registered");
        super.onRegister();
    }

    public void sendMessage(String text, Long chatId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        execute(sendMessage);
    }
}
