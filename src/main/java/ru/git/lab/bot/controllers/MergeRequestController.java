package ru.git.lab.bot.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.services.api.MergeRequestService;

@Slf4j
@RestController
@RequestMapping("/merges/requests")
@RequiredArgsConstructor
public class MergeRequestController {

    private final MergeRequestService saveGitUserDecorator;

    @PostMapping
    public void mergeRequestEvent(@RequestBody MergeRequestEvent request) {
        saveGitUserDecorator.handleEvent(request);
    }
}
