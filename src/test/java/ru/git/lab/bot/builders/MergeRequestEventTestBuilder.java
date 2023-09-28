package ru.git.lab.bot.builders;

import ru.git.lab.bot.api.mr.Assignee;
import ru.git.lab.bot.api.mr.Changes;
import ru.git.lab.bot.api.mr.Label;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.Project;
import ru.git.lab.bot.api.mr.Repository;
import ru.git.lab.bot.api.mr.Reviewer;
import ru.git.lab.bot.api.mr.User;

import java.util.List;

public class MergeRequestEventTestBuilder {

    private String objectKind;

    private String eventType;

    private User user;

    private Project project;

    private Repository repository;

    private ObjectAttributes objectAttributes;

    private List<Label> labels;

    private Changes changes;

    private List<Assignee> assignees;

    private List<Reviewer> reviewers;

    public static MergeRequestEventTestBuilder builder() {return new MergeRequestEventTestBuilder();}

    public MergeRequestEventTestBuilder objectKind(String objectKind) {
        this.objectKind = objectKind;
        return this;
    }

    public MergeRequestEventTestBuilder eventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public MergeRequestEventTestBuilder user(User user) {
        this.user = user;
        return this;
    }

    public MergeRequestEventTestBuilder project(Project project) {
        this.project = project;
        return this;
    }

    public MergeRequestEventTestBuilder repository(Repository repository) {
        this.repository = repository;
        return this;
    }

    public MergeRequestEventTestBuilder objectAttributes(ObjectAttributes objectAttributes) {
        this.objectAttributes = objectAttributes;
        return this;
    }

    public MergeRequestEventTestBuilder labels(List<Label> labels) {
        this.labels = labels;
        return this;
    }

    public MergeRequestEventTestBuilder changes(Changes changes) {
        this.changes = changes;
        return this;
    }

    public MergeRequestEventTestBuilder assignees(List<Assignee> assignees) {
        this.assignees = assignees;
        return this;
    }

    public MergeRequestEventTestBuilder reviewers(List<Reviewer> reviewers) {
        this.reviewers = reviewers;
        return this;
    }

    public MergeRequestEvent build() {
        MergeRequestEvent event = new MergeRequestEvent();

        event.setObjectKind(objectKind);
        event.setEventType(eventType);
        event.setUser(user);
        event.setProject(project);
        event.setRepository(repository);
        event.setObjectAttributes(objectAttributes);
        event.setLabels(labels);
        event.setChanges(changes);
        event.setAssignees(assignees);
        event.setReviewers(reviewers);

        return event;
    }
}
