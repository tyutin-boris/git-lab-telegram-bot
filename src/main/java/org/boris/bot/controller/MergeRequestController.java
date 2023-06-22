package org.boris.bot.controller;

import org.boris.bot.api.MergeRequest;
import org.boris.bot.services.MergeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merges/requests")
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    public MergeRequestController(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }

    @PostMapping
    public void mergeRequestEvent(@RequestBody MergeRequest request) {
        mergeRequestService.sendMergeRequest(request);
    }
}
