package ru.git.lab.bot.services.pipelines;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.model.entities.PipelineHistoryEntity;
import ru.git.lab.bot.model.repository.PipelineHistoryRepository;
import ru.git.lab.bot.services.pipelines.api.PipelineHistoryService;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineHistoryServiceImpl implements PipelineHistoryService {

    private final PipelineHistoryRepository pipelineHistoryRepository;

    @Override
    public void save(String message) {
        PipelineHistoryEntity pipelineHistoryEntity = new PipelineHistoryEntity();

        pipelineHistoryEntity.setMessage(message);
        pipelineHistoryEntity.setCreateDate(OffsetDateTime.now());

        pipelineHistoryRepository.save(pipelineHistoryEntity);
        log.debug("Сохранено pipeline сообшение");
    }
}
