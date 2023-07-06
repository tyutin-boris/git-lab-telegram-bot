package org.boris.bot.utils;

import org.boris.bot.api.Action;
import org.boris.bot.api.MergeRequest;
import org.boris.bot.api.ObjectAttributes;

import java.util.Optional;

import static org.boris.bot.api.Action.INDEFINITELY;

public class ObjectAttributesUtils {

    public static ObjectAttributes getObjectAttributes(MergeRequest request) {
        return Optional.of(request)
                .map(MergeRequest::getObjectAttributes)
                .orElseThrow(() -> new RuntimeException("Merge request without object attributes"));
    }

    public static Action getAction(ObjectAttributes objectAttributes) {
        return Optional.ofNullable(objectAttributes.getAction())
                .orElse(INDEFINITELY);
    }
}
