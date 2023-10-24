package ru.git.lab.bot.services.pipelines;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.pipeline.PipelineStatus;
import ru.git.lab.bot.services.pipelines.api.PipelineStatusTextService;

@Service
@RequiredArgsConstructor
public class PipelineStatusTextServiceImpl implements PipelineStatusTextService {

    private static final String crossMark = EmojiParser.parseToUnicode(":x:");

    private static final String whiteCheckMark = EmojiParser.parseToUnicode(":white_check_mark:");

    private static final String fastForward = EmojiParser.parseToUnicode(":fast_forward:");

    private static final String pauseButton = EmojiParser.parseToUnicode(":pause_button:");

    @Override
    public String createText(PipelineStatus status) {
        String emoji = switch (status) {
            case PENDING -> pauseButton;
            case RUNNING -> fastForward;
            case SUCCESS -> whiteCheckMark;
            case FAILED -> crossMark;
        };

        return "\n\n<b>Pipeline status:</b> " + status.getValue() + " " + emoji + "\n";
    }
}
