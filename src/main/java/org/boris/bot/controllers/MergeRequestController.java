package org.boris.bot.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boris.bot.api.MergeRequest;
import org.boris.bot.services.MergeRequestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/merges/requests")
@RequiredArgsConstructor
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    @PostMapping
    public void mergeRequestEvent(@RequestBody MergeRequest request) {
        log.debug(request.toString());
        mergeRequestService.sendMergeRequestMessage(request);
    }
}
