package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.model.repository.ChatRepository;
import ru.git.lab.bot.services.MessageService;
import ru.git.lab.bot.services.handlers.mr.MrEventHandler;
import ru.git.lab.bot.services.senders.MergeRequestSender;
import ru.git.lab.bot.utils.UserUtils;

import java.util.List;
import java.util.Optional;

import static ru.git.lab.bot.api.mr.Action.REOPEN;
import static ru.git.lab.bot.utils.MergeRequestUtils.createMergeRequestMessage;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrReopenEventHandler implements MrEventHandler {

    private final MergeRequestSender sender;
    private final MessageService messageService;
    private final ChatRepository chatRepository;

    @Override
    public void handleEvent(MergeRequestEvent request) {
        ObjectAttributes objectAttributes = getObjectAttributes(request);
        Long mrId = objectAttributes.getId();
        User user = UserUtils.getUser(request);

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);

        String text = createMergeRequestMessage(request);
        List<Long> chatsId = chatRepository.getAllChatId();

        for (Long id : chatsId) {
            Message message = sender.sendMessage(text, id);
            Optional.ofNullable(message)
                    .ifPresent(m -> {
                        messageService.saveMessage(m, objectAttributes, user);
                    });
        }
    }

    @Override
    public Action getAction() {
        return REOPEN;
    }
}