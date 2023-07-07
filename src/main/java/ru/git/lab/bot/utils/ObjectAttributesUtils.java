package ru.git.lab.bot.utils;

import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequest;
import ru.git.lab.bot.api.mr.ObjectAttributes;

import java.util.Optional;

public class ObjectAttributesUtils {

    public static ObjectAttributes getObjectAttributes(MergeRequest request) {
        return Optional.of(request)
                .map(MergeRequest::getObjectAttributes)
                .orElseThrow(() -> new RuntimeException("Merge request without object attributes"));
    }

    public static Action getAction(ObjectAttributes objectAttributes) {
        return Optional.ofNullable(objectAttributes.getAction())
                .orElse(Action.INDEFINITELY);
    }
}
