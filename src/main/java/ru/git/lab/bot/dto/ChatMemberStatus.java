package ru.git.lab.bot.dto;

import java.util.Optional;

public enum ChatMemberStatus {
    KICKED, ADMINISTRATOR;

    public static ChatMemberStatus stringToStatus(String memberStatus) {
        String status = Optional.ofNullable(memberStatus).orElseThrow(
                () -> new RuntimeException("Member status is null, casting failed"));

        switch (status) {
            case "administrator":
                return ChatMemberStatus.ADMINISTRATOR;
            case "kicked":
                return ChatMemberStatus.KICKED;
            default:
                throw new RuntimeException("No found enum type for member status: " + status);
        }
    }
}
