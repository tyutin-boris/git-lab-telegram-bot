package ru.git.lab.bot.services.pipelines;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.pipeline.PipelineStatus;
import ru.git.lab.bot.model.entities.PipelineEntity;
import ru.git.lab.bot.model.repository.PipelineRepository;
import ru.git.lab.bot.services.pipelines.api.PipelineService;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {

    private final PipelineRepository pipelineRepository;

    @Override
    public Optional<PipelineStatus> getStatusForNewestByMrId(Long mrId) {
        return pipelineRepository.findTopByMrIdOrderByCreateDateDesc(mrId)
                .map(PipelineEntity::getStatus);
    }

    @Override
    public void save(Long mrId, PipelineStatus status, String text) {
        PipelineEntity pipelineEntity = new PipelineEntity();

        pipelineEntity.setMrId(mrId);
        pipelineEntity.setStatus(status);
        pipelineEntity.setText(text);
        pipelineEntity.setCreateDate(OffsetDateTime.now());

        pipelineRepository.save(pipelineEntity);
    }
}
