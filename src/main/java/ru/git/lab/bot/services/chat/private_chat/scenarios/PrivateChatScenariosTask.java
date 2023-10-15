package ru.git.lab.bot.services.chat.private_chat.scenarios;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrivateChatScenariosTask {

    START(0),

    REQUEST_USERNAME(0),

    RECEIVE_USERNAME(1),

    REQUEST_CHAT_NAME(0),

    RECEIVE_CHAT_NAME(1);

    private final Integer number;
}
