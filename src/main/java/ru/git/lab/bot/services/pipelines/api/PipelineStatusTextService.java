package ru.git.lab.bot.services.pipelines.api;

import ru.git.lab.bot.api.pipeline.PipelineStatus;

public interface PipelineStatusTextService {

    String createText(PipelineStatus status);
}
