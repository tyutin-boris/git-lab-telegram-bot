package org.boris.bot.controller;

import lombok.SneakyThrows;
import org.boris.bot.bot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merges/requests")
public class MergeRequestController {

    @Autowired
    private final TelegramBot bot;

    public MergeRequestController(TelegramBot bot) {this.bot = bot;}

    @SneakyThrows
    @PostMapping
    public void mergeRequestEvent(@RequestBody String request) {
//    public void mergeRequestEvent(@RequestBody String request) {
//        System.out.println(request);

        bot.sendAction();
    }
}
