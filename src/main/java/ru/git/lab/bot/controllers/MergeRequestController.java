package ru.git.lab.bot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.services.MergeRequestService;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/merges/requests")
@RequiredArgsConstructor
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public void mergeRequestEvent(@RequestBody String request) throws IOException {
        log.debug(request);
        MergeRequestEvent mergeRequestEvent = objectMapper.readValue(request, MergeRequestEvent.class);
        mergeRequestService.handleEvent(mergeRequestEvent);
    }
}
