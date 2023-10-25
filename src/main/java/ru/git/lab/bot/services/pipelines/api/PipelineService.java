package ru.git.lab.bot.services.pipelines.api;

import ru.git.lab.bot.api.pipeline.PipelineEvent;
import ru.git.lab.bot.api.pipeline.PipelineStatus;

public interface PipelineService {

    PipelineStatus getStatusForNewestByMrId(Long mrId);

    void save(Long mrId, PipelineStatus status, String text);
}
