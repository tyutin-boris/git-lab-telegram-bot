package ru.git.lab.bot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.git.lab.bot.model.entities.TgMessageHistoryEntity;
import ru.git.lab.bot.model.repository.TgMessageHistoryRepository;
import ru.git.lab.bot.services.api.TgMessageHistoryService;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TgMessageHistoryServiceImpl implements TgMessageHistoryService {

    private final static String ERROR = "Error";

    private final ObjectMapper objectMapper;

    private final TgMessageHistoryRepository tgMessageHistoryRepository;

    @Override
    public void save(Update update) {
        String text = getText(update);
        TgMessageHistoryEntity entity = new TgMessageHistoryEntity();

        entity.setText(text);
        entity.setCreateDate(OffsetDateTime.now());

        tgMessageHistoryRepository.save(entity);
    }

    private String getText(Update update) {
        try {
            return Optional.ofNullable(objectMapper.writeValueAsString(update))
                    .orElse(ERROR);
        } catch (JsonProcessingException e) {
            log.error("Failed writing mr object to string for MR history", e);
            return ERROR;
        }
    }
}
