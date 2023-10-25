package ru.git.lab.bot.services.pipelines;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.api.pipeline.MergeRequest;
import ru.git.lab.bot.api.pipeline.PipeObjectAttributes;
import ru.git.lab.bot.api.pipeline.PipelineEvent;
import ru.git.lab.bot.api.pipeline.PipelineStatus;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;
import ru.git.lab.bot.model.repository.TgGitUsersRepository;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.api.TgMrMessageService;
import ru.git.lab.bot.services.pipelines.api.PipelineHandler;
import ru.git.lab.bot.services.pipelines.api.PipelineService;
import ru.git.lab.bot.services.pipelines.api.PipelineTestMessageService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineHandlerImpl implements PipelineHandler {

    private final MessageSender messageSender;

    private final TgMrMessageService tgMrMessageService;

    private final MrTextMessageService mrTextMessageService;

    private final TgGitUsersRepository tgGitUsersRepository;

    private final PipelineTestMessageService pipelineTestMessageService;

    private final PipelineService pipelineService;

    @Override
    public void handle(PipelineEvent event) {
        Optional<PipelineEvent> eventOpt = Optional.ofNullable(event);

        PipelineStatus status = getStatus(eventOpt);
        Long mrId = getMrId(eventOpt);
        String text = pipelineTestMessageService.createText(event);

        pipelineService.save(mrId, status, text);

        if (isDraft(eventOpt)) {
            log.debug("Mr has draft");
            return;
        }

        String username = getUsername(eventOpt, mrId);
        switch (status) {
            case PENDING, RUNNING -> pipelineLog(mrId, status);
            case FAILED, SUCCESS -> sendPipelineStatusNotification(username, text);
        }

        TgMrMessageEntity tgMrMessage = tgMrMessageService.getByMrId(mrId);

        if (tgMrMessage.isDraft() && tgMrMessage.isDelete()) {
            log.debug("Mr id: {} is draft or delete. Message pipeline status not update", mrId);
            return;
        }

        String message = mrTextMessageService.createMrMessage(mrId);

        tgMrMessage.getChats()
                .forEach(chat -> {
                    messageSender.updateMessage(message, chat.getChatId(), chat.getTgId());
                });
    }

    boolean isDraft(Optional<PipelineEvent> event) {
        return event.map(PipelineEvent::getMergeRequest)
                .map(MergeRequest::getTitle)
                .map(title -> title.contains("Draft: "))
                .orElse(false);
    }

    private static String getUsername(Optional<PipelineEvent> eventOpt, Long mrId) {
        return eventOpt.map(PipelineEvent::getUser)
                .map(User::getUsername)
                .orElseThrow(() -> new RuntimeException("User name not found. mrId: " + mrId));
    }

    private PipelineStatus getStatus(Optional<PipelineEvent> eventOpt) {
        return eventOpt.map(PipelineEvent::getObjectAttributes)
                .map(PipeObjectAttributes::getStatus)
                .orElseThrow(() -> new RuntimeException("Pipeline notification no sent. Pipeline status not found."));
    }

    private Long getMrId(Optional<PipelineEvent> eventOpt) {
        return eventOpt.map(PipelineEvent::getMergeRequest)
                .map(MergeRequest::getId)
                .orElseThrow(() -> new RuntimeException("Mr id not found"));
    }

    private void sendPipelineStatusNotification(String username, String text) {
        tgGitUsersRepository.findByGitUsername(username)
                .forEach(user -> {
                    Long chatId = user.getTgId();
                    messageSender.sendMessage(text, chatId);
                });
    }

    private void pipelineLog(Long mrId, PipelineStatus status) {
        log.debug("Received pipeline event with status {} for mr id: {}", status, mrId);
    }
}
