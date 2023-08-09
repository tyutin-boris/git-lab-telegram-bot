package ru.git.lab.bot.services.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.git.lab.bot.config.BotConfig;
import ru.git.lab.bot.services.handlers.member.UpdateHandler;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final List<UpdateHandler> handlers;
    private final List<BotCommand> botCommands;

    @PostConstruct
    private void setUp() {
        try {
            execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("failed to add bot command", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Start handling update event");

        if (CollectionUtils.isEmpty(handlers)) {
            log.warn("Update handlers not found");
        }

        Optional.ofNullable(update.getMyChatMember()).ifPresent(chatMember -> {
            handlers.forEach(handler -> handler.handle(chatMember));
        });

        Optional.ofNullable(update.getMessage()).ifPresent(this::handle);

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

//TODO https://habr.com/ru/articles/481354/
    private void handle(Message message) {
        switch (message.getText()) {
            case "/add_user":
                break;
            default:
//                "Sorry, command was not recognized"
        }
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
        log.info("Bot was registered");
        super.onRegister();
    }

    public Message sendMessage(String text, Long chatId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.enableHtml(true);
        log.debug("Send message to chat with id " + chatId);
        return execute(sendMessage);
    }

    public boolean deleteMessage(Long chatId, Integer messageId) throws TelegramApiException {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        return execute(deleteMessage);
    }

    public Message updateMessage(String text, Long chatId, Integer messageId) throws TelegramApiException {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(text);
        editMessageText.enableHtml(true);
        return (Message) execute(editMessageText);
    }
}
