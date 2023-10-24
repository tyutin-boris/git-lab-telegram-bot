package ru.git.lab.bot.services.pipelines.api;

import ru.git.lab.bot.api.pipeline.PipelineEvent;
import ru.git.lab.bot.api.pipeline.PipelineStatus;

public interface PipelineService {

    void handle(PipelineEvent event);

    PipelineStatus getStatusForNewestByMrId(Long mrId);
}
