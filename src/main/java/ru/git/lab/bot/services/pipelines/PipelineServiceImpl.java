package ru.git.lab.bot.services.pipelines;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.pipeline.PipelineEvent;
import ru.git.lab.bot.api.pipeline.PipelineStatus;
import ru.git.lab.bot.model.repository.TgGitUsersRepository;
import ru.git.lab.bot.services.pipelines.api.PipelineService;
import ru.git.lab.bot.services.senders.api.MessageSender;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {

    private final MessageSender messageSender;

    private final TgGitUsersRepository tgGitUsersRepository;

    @Override
    public void handle(PipelineEvent event) {
        System.out.println();

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

        PipelineStatus status = event.getObjectAttributes()
                .getStatus();
        String username = event.getUser()
                .getUsername();

        tgGitUsersRepository.findByGitUsername(username)
                .forEach(user -> {
                    String text = "Piple status " + status;
                    Long chatId = -1002018570984L;
                    messageSender.sendMessage(text, chatId);
                });
    }

    private void pipelineLog(Long mrId, PipelineStatus status) {
        log.debug("Received pipeline event with status {} for mr id: {}", status, mrId);
    }
}
