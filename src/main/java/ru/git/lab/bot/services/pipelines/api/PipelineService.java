package ru.git.lab.bot.services.pipelines.api;

import ru.git.lab.bot.api.pipeline.PipelineEvent;

public interface PipelineService {

    void handle(PipelineEvent event);
}
