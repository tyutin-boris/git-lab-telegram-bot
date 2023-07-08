package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequest;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.services.MessageService;
import ru.git.lab.bot.services.handlers.mr.MrActionHandler;
import ru.git.lab.bot.services.senders.MergeRequestSender;
import ru.git.lab.bot.utils.UserUtils;

import java.util.List;

import static ru.git.lab.bot.api.mr.Action.MERGE;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrMergeActionHandler implements MrActionHandler {

    private final MessageService messageService;

    @Override
    public void handleAction(MergeRequest request) {
        Long mrId = getObjectAttributes(request).getId();
        User user = UserUtils.getUser(request);
        String email = user.getEmail();
        String username = user.getUsername();

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);

        List<MessageToDelete> messages = messageService.getMessageToDelete(mrId, email, username);
        messageService.deleteMessages(messages);
    }

    @Override
    public Action getAction() {
        return MERGE;
    }
}
