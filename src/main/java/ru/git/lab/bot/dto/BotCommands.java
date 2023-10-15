package ru.git.lab.bot.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.git.lab.bot.services.chat.private_chat.scenarios.PrivateChatScenariosTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum BotCommands {

    START("/start", "First command", new HashMap<>()),
    ADD_GIT_USERNAME("/add_git_username", "Add git username", getScenariosForAddGitUsername()),
    ADD_CHAT_TO_RECEIVE_GITLAB_NOTIFICATIONS("/add_chat_name",
            "Add chat to receive notifications from gitlab", getScenariosForAddChat());

    private final String command;
    private final String description;
    private final Map<PrivateChatScenariosTask, Integer> taskNumber;

    public static Optional<BotCommands> getCommand(String text) {
        for (BotCommands command : BotCommands.values()) {
            if (command.getCommand().equals(text)) {
                return Optional.of(command);
            }
        }
        return Optional.empty();
    }

    private static Map<PrivateChatScenariosTask, Integer> getScenariosForAddGitUsername() {
        return new HashMap<>() {{
            PrivateChatScenariosTask requestUsername = PrivateChatScenariosTask.REQUEST_USERNAME;
            PrivateChatScenariosTask receiveUsername = PrivateChatScenariosTask.RECEIVE_USERNAME;
            put(requestUsername, requestUsername.getNumber());
            put(receiveUsername, receiveUsername.getNumber());
        }};
    }

    private static Map<PrivateChatScenariosTask, Integer> getScenariosForAddChat() {
        return new HashMap<>() {{
            PrivateChatScenariosTask requestChatName = PrivateChatScenariosTask.REQUEST_CHAT_NAME;
            PrivateChatScenariosTask receiveChatName = PrivateChatScenariosTask.RECEIVE_CHAT_NAME;
            put(requestChatName, requestChatName.getNumber());
            put(receiveChatName, receiveChatName.getNumber());
        }};
    }
}
