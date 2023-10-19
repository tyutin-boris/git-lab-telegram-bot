package ru.git.lab.bot.services.mr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.services.api.TgMrMessageService;
import ru.git.lab.bot.services.mr.api.CloseMrService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloseMrServiceImpl implements CloseMrService {

    private final TgMrMessageService messageService;

    @Override
    public void deleteMessage(long mrId, long authorId) {
        List<MessageToDelete> messages = messageService.getMessageToDelete(mrId, authorId);
        messageService.deleteMessages(messages);
    }
}
