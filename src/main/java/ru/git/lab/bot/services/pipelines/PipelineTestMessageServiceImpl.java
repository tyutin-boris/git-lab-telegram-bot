package ru.git.lab.bot.services.pipelines;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.pipeline.MergeRequest;
import ru.git.lab.bot.api.pipeline.PipelineEvent;
import ru.git.lab.bot.services.pipelines.api.PipelineStatusTextService;
import ru.git.lab.bot.services.pipelines.api.PipelineTestMessageService;

@Service
@RequiredArgsConstructor
public class PipelineTestMessageServiceImpl implements PipelineTestMessageService {

    private final PipelineStatusTextService pipelineStatusTextService;

    @Override
    public String createText(PipelineEvent event) {

        MergeRequest mergeRequest = event.getMergeRequest();

        String status = pipelineStatusTextService.createText(event.getObjectAttributes()
                                                                     .getStatus());

        String title = getTitleText(mergeRequest.getTitle());
        String url = getMrUrlText(mergeRequest.getUrl());

        return status + title + url;
    }

    private String getTitleText(String title) {
        return "\n<b>Title:</b> " + title + "\n\n";
    }

    private String getMrUrlText(String url) {
        return "<a href='" + url + "'><b><u>MR Hyperlink</u></b></a>";
    }
}
