package ru.git.lab.bot.controllers;

import lombok.RequiredArgsConstructor;
import ru.git.lab.bot.dto.ChatDto;
import ru.git.lab.bot.services.api.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public List<ChatDto> getAll() {
        return chatService.getAll();
    }

    @GetMapping("/{chatId}")
    public ChatDto getById(@PathVariable Long chatId) {
        return chatService.getById(chatId);
    }
}
