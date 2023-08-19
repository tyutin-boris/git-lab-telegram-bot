package ru.git.lab.bot.dto;

import java.util.Optional;

public enum ChatType {
    PRIVATE,
    GROUP,
    CHANNEL,
    SUPERGROUP;

    public static ChatType stringToChatType(String chatType) {

        String type = Optional.ofNullable(chatType).orElseThrow(
                () -> new RuntimeException("Type of chat is null, casting failed"));

        switch (type) {
            case "private":
                return ChatType.PRIVATE;
            case "group":
                return ChatType.GROUP;
            case "channel":
                return ChatType.CHANNEL;
            case "supergroup":
                return ChatType.SUPERGROUP;
            default:
                throw new RuntimeException("No found enum type for chat type: " + type);
        }
    }
}
