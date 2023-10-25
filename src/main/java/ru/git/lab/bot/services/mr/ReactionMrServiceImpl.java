package ru.git.lab.bot.services.mr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.api.TgMrMessageService;
import ru.git.lab.bot.services.mr.api.ReactionMrService;
import ru.git.lab.bot.services.senders.api.MessageSender;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionMrServiceImpl implements ReactionMrService {

    private final MessageSender sender;

    private final TgMrMessageService messageService;

    private final MrTextMessageService mrTextMessageService;

    @Override
    public void addReactionToMessage(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        long authorId = mergeRequest.getAuthor()
                .getId();

        String text = mrTextMessageService.createMrMessage(mrId);

        messageService.getMessageByMrIdAndAuthorId(mrId, authorId)
                .getChats()
                .forEach(chat -> sender.updateMessage(text, chat.getChatId(), chat.getTgId()));
    }
}
