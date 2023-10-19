package ru.git.lab.bot.services.pipelines;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.model.entities.PipelineHistoryEntity;
import ru.git.lab.bot.model.repository.PipelineHistoryRepository;
import ru.git.lab.bot.services.pipelines.api.PipelineHistoryService;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineHistoryServiceImpl implements PipelineHistoryService {

    private final PipelineHistoryRepository pipelineHistoryRepository;

    @Override
    public void save(String message) {
        PipelineHistoryEntity pipelineHistoryEntity = new PipelineHistoryEntity();

        String receivedMessage = Optional.ofNullable(message)
                .orElse("Error");

        pipelineHistoryEntity.setMessage(receivedMessage);
        pipelineHistoryEntity.setCreateDate(OffsetDateTime.now());

        pipelineHistoryRepository.save(pipelineHistoryEntity);
        log.debug("Save pipeline message");
    }
}
