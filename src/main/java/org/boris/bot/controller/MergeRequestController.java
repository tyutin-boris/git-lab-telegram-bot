package org.boris.bot.controller;

import org.boris.bot.api.MergeRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merges/requests")
public class MergeRequestController {

    @PostMapping
    public void mergeRequestEvent(@RequestBody MergeRequest request) {
//    public void mergeRequestEvent(@RequestBody String request) {
        System.out.println(request);
    }
}
