package ru.git.lab.bot.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.services.ChatService;
import ru.git.lab.bot.services.EventOfCreateMrService;
import ru.git.lab.bot.services.MessageService;
import ru.git.lab.bot.services.senders.MessageSender;

import java.util.List;
import java.util.Optional;

import static ru.git.lab.bot.utils.MergeRequestUtils.createMergeRequestMessage;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventOfCreateMrServiceImpl implements EventOfCreateMrService {

    private final MessageSender sender;
    private final ChatService chatService;
    private final MessageService messageService;

    @Override
    public void sendAndSaveMessage(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        String text = createMergeRequestMessage(event);

        List<Long> chatsId = chatService.getAllChatId();

        for (Long id : chatsId) {
            Message message = sender.sendMessage(text, id);
            Optional.ofNullable(message)
                    .ifPresent(m -> messageService.saveMessage(m, objectAttributes));
        }
    }
}
