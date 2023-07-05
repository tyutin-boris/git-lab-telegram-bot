package org.boris.bot.controller;

import lombok.extern.slf4j.Slf4j;
import org.boris.bot.api.Action;
import org.boris.bot.api.MergeRequest;
import org.boris.bot.api.ObjectAttributes;
import org.boris.bot.services.MergeRequestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.boris.bot.api.Action.INDEFINITELY;

@Slf4j
@RestController
@RequestMapping("/merges/requests")
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    public MergeRequestController(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }

    @PostMapping
    public void mergeRequestEvent(@RequestBody MergeRequest request) {
        log.debug(request.toString());
        Action action = Optional.of(request)
                .map(MergeRequest::getObjectAttributes)
                .map(ObjectAttributes::getAction)
                .orElse(INDEFINITELY);
        System.out.println();

//        if ("open".equalsIgnoreCase(action)) {
//        } else if ("update".equalsIgnoreCase(action)) {
//            log.debug("Get MR with action status update");
//        } else if ("close".equals(action)) {
//
//        }
        switch (action) {
            case OPEN:
                mergeRequestService.sendMergeRequestOpen(request);
                break;
            case CLOSE:
                mergeRequestService.sendMergeRequestClose(request);
                break;
            default:
                log.warn("Merge request action not defined");
                break;
        }
    }
}
