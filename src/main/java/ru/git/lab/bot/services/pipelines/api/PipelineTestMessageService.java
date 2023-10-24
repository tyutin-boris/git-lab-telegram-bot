package ru.git.lab.bot.services.pipelines.api;

import ru.git.lab.bot.api.pipeline.PipelineEvent;

public interface PipelineTestMessageService {

    String createText(PipelineEvent event);
}
