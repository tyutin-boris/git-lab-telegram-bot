package ru.git.lab.bot.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum ChatType {
    PRIVATE("private"),
    GROUP("group"),
    CHANNEL("channel"),
    SUPERGROUP("supergroup");

    private final String name;

    public static ChatType stringToChatType(String chatType) {
        String type = Optional.ofNullable(chatType).orElseThrow(
                () -> new RuntimeException("Type of chat is null, casting failed"));

        return switch (type) {
            case "private" -> ChatType.PRIVATE;
            case "group" -> ChatType.GROUP;
            case "channel" -> ChatType.CHANNEL;
            case "supergroup" -> ChatType.SUPERGROUP;
            default -> throw new RuntimeException("No found enum type for chat type: " + type);
        };
    }
}
