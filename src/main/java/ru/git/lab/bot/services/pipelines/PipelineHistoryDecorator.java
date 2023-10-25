package ru.git.lab.bot.services.pipelines;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.pipeline.PipelineEvent;
import ru.git.lab.bot.services.pipelines.api.PipelineHandler;
import ru.git.lab.bot.services.pipelines.api.PipelineHistoryService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineHistoryDecorator implements PipelineHandler {

    private final static String ERROR = "Error";

    private final ObjectMapper objectMapper;

    private final PipelineHandler pipelineHandlerImpl;

    private final PipelineHistoryService pipelineHistoryService;

    @Override
    public void handle(PipelineEvent event) {
        String pipeline = getPipeline(event);
        pipelineHistoryService.save(pipeline);

        pipelineHandlerImpl.handle(event);
    }

    private String getPipeline(PipelineEvent event) {
        try {
            return Optional.ofNullable(objectMapper.writeValueAsString(event))
                    .orElse(ERROR);
        } catch (JsonProcessingException e) {
            log.error("Failed writing pipeline object to string for history", e);
            return ERROR;
        }
    }
}
