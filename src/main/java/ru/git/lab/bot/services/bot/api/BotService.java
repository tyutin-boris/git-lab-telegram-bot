package ru.git.lab.bot.services.bot.api;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.git.lab.bot.dto.ChatResponse;

import java.util.Optional;

public interface BotService {

    Optional<ChatResponse> handleReceivedUpdate(Update update);
}
