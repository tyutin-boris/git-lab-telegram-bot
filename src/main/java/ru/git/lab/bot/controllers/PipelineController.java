package ru.git.lab.bot.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.git.lab.bot.services.pipelines.api.PipelineHistoryService;

@Slf4j
@RestController
@RequestMapping("/pipelines")
@RequiredArgsConstructor
public class PipelineController {

    private final PipelineHistoryService pipelineHistoryService;

    @PostMapping("/histories")
    public void savePipelineHistory(@RequestBody String message) {
        log.debug("Received pipeline message");
        pipelineHistoryService.save(message);
    }
}
