package ru.git.lab.bot.utils;

import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.User;

import java.util.Optional;

public class UserUtils {

    public static User getUser(MergeRequestEvent request) {
        return Optional.ofNullable(request)
                .map(MergeRequestEvent::getUser)
                .orElseThrow(() -> new RuntimeException("User not present"));
    }
}
