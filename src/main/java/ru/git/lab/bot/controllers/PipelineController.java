package ru.git.lab.bot.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.git.lab.bot.api.pipeline.PipelineEvent;
import ru.git.lab.bot.services.pipelines.api.PipelineService;

@Slf4j
@RestController
@RequestMapping("/pipelines")
@RequiredArgsConstructor
public class PipelineController {

    private final PipelineService pipelineHistoryDecorator;

    @PostMapping("/histories")
    public void savePipelineHistory(@RequestBody PipelineEvent event) {
        log.debug("Received pipeline message");
        pipelineHistoryDecorator.handle(event);
    }
}
