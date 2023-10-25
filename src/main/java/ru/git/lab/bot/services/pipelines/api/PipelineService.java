package ru.git.lab.bot.services.pipelines.api;

import ru.git.lab.bot.api.pipeline.PipelineStatus;

import java.util.Optional;

public interface PipelineService {

    Optional<PipelineStatus> getStatusForNewestByMrId(Long mrId);

    void save(Long mrId, PipelineStatus status, String text);
}
