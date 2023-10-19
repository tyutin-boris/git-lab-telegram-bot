package ru.git.lab.bot.services.mr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.services.api.MergeRequestService;
import ru.git.lab.bot.services.mr.api.MergeRequestHistoryService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MergeRequestHistoryDecorator implements MergeRequestService {
    private final static String ERROR = "Error";


    private final ObjectMapper objectMapper;

    private final MergeRequestService mergeRequestServiceImpl;

    private final MergeRequestHistoryService mergeRequestHistoryService;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        log.debug("Try save MR history");
        String message = getMessage(event);
        mergeRequestHistoryService.save(message);

        mergeRequestServiceImpl.handleEvent(event);
    }

    private String getMessage(MergeRequestEvent event) {
        try {
            return Optional.ofNullable(objectMapper.writeValueAsString(event))
                    .orElse(ERROR);
        } catch (JsonProcessingException e) {
            log.error("Failed writing mr object to string for MR history", e);
            return ERROR;
        }
    }
}
