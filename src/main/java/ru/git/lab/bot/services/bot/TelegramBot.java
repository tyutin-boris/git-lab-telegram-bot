package ru.git.lab.bot.services.bot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.git.lab.bot.config.BotConfig;
import ru.git.lab.bot.services.bot.api.BotService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final BotService botService;
    private final List<BotCommand> botCommands;

    @PostConstruct
    private void setUp() {
        try {
            String languageCode = null;
            BotCommandScopeDefault scope = new BotCommandScopeDefault();
            SetMyCommands method = new SetMyCommands(botCommands, scope, languageCode);

            execute(method);
            log.info("Add command to boot bot: " + botCommands);
        } catch (TelegramApiException e) {
            log.error("Failed to add bot command", e);
        }
    }

    @Override
    public void onUpdateReceived(Update recivedUpdate) {
        log.debug("Start handling update event");

        Integer updateId = Optional.ofNullable(recivedUpdate)
                .map(botService::handleReceivedUpdate)
                .orElseThrow(() -> new RuntimeException("Update is null"));

        log.debug("End handling update with id " + updateId);
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
}
