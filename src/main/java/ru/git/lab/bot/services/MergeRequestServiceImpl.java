package ru.git.lab.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.mappers.MergeRequestMapper;
import ru.git.lab.bot.services.api.MergeRequestService;
import ru.git.lab.bot.services.api.GitUserService;
import ru.git.lab.bot.services.mr.handlers.api.MrEventHandler;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MergeRequestServiceImpl implements MergeRequestService {

    private final GitUserService gitUserService;
    private final MergeRequestMapper mergeRequestMapper;
    private final Map<Action, MrEventHandler> eventHandlers;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        MergeRequestDto mergeRequest = mergeRequestMapper.toDto(event);

        gitUserService.saveUserIfNotExist(mergeRequest.getUser());
        eventHandlers.get(mergeRequest.getAction()).handleEvent(mergeRequest);
    }
}
