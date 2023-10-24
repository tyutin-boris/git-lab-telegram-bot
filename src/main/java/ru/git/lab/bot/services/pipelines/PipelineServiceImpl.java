package ru.git.lab.bot.services.pipelines;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.api.pipeline.PipeObjectAttributes;
import ru.git.lab.bot.api.pipeline.PipelineEvent;
import ru.git.lab.bot.api.pipeline.PipelineStatus;
import ru.git.lab.bot.model.repository.TgGitUsersRepository;
import ru.git.lab.bot.services.pipelines.api.PipelineService;
import ru.git.lab.bot.services.pipelines.api.PipelineTestMessageService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {

    private final MessageSender messageSender;

    private final PipelineTestMessageService pipelineTestMessageService;

    private final TgGitUsersRepository tgGitUsersRepository;

    @Override
    public void handle(PipelineEvent event) {
        PipelineStatus status = event.getObjectAttributes()
                .getStatus();

        Long mrId = event.getMergeRequest()
                .getId();

        switch (status) {
            case PENDING, RUNNING -> pipelineLog(mrId, status);
            case FAILED, SUCCESS -> sendPipelineStatusNotification(event);
        }
    }

    private void sendPipelineStatusNotification(PipelineEvent event) {
        Optional<PipelineEvent> eventOpt = Optional.ofNullable(event);

        Optional<PipelineStatus> status = eventOpt.map(PipelineEvent::getObjectAttributes)
                .map(PipeObjectAttributes::getStatus);

        if (status.isEmpty()) {
            log.error("Pipeline notification no sent. Pipeline status not found.");
            return;
        }

        Optional<String> username = eventOpt.map(PipelineEvent::getUser)
                .map(User::getUsername);

        if (username.isEmpty()) {
            log.error("Pipeline notification no sent. Username not found.");
            return;
        }

        boolean isDraft = event.getMergeRequest()
                .getTitle()
                .contains("Draft: ");

        if (!isDraft) {
            tgGitUsersRepository.findByGitUsername(username.get())
                    .forEach(user -> {
                        String text = pipelineTestMessageService.createText(event);
                        Long chatId = user.getTgId();
                        messageSender.sendMessage(text, chatId);
                    });
        }
    }

    private void pipelineLog(Long mrId, PipelineStatus status) {
        log.debug("Received pipeline event with status {} for mr id: {}", status, mrId);
    }
}
