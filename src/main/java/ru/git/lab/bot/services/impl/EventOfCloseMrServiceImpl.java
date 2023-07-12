package ru.git.lab.bot.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.services.EventOfCloseMrService;
import ru.git.lab.bot.services.MessageService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventOfCloseMrServiceImpl implements EventOfCloseMrService {

    private final MessageService messageService;

    @Override
    public void deleteMessage(Long mrId, Long authorId) {
        List<MessageToDelete> messages = messageService.getMessageToDelete(mrId, authorId);
        messageService.deleteMessages(messages);
    }
}
