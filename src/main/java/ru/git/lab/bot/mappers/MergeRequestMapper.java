package ru.git.lab.bot.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.Reviewer;
import ru.git.lab.bot.dto.MergeRequestDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class MergeRequestMapper implements ToDtoMapper<MergeRequestEvent, MergeRequestDto> {

    @Override
    @Mapping(source = "objectAttributes.id", target = "mrId")
    @Mapping(source = "objectAttributes.title", target = "title")
    @Mapping(source = "objectAttributes.description", target = "description")
    @Mapping(source = "objectAttributes.sourceBranch", target = "sourceBranch")
    @Mapping(source = "objectAttributes.targetBranch", target = "targetBranch")
    @Mapping(source = "objectAttributes.url", target = "mrUrl")
    @Mapping(source = "objectAttributes.action", target = "action", defaultValue = "INDEFINITELY")
    @Mapping(source = "objectAttributes.title", target = "draft", qualifiedByName = "getIsDraft")
    @Mapping(source = "objectAttributes.authorId", target = "author.id")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "reviewers", target = "reviewerName", qualifiedByName = "getReviewerName")
    public abstract MergeRequestDto toDto(MergeRequestEvent event);

    @Named("getReviewerName")
    public String getReviewerName(List<Reviewer> reviewers) {
        return Optional.ofNullable(reviewers)
                .orElse(Collections.emptyList())
                .stream()
                .findFirst()
                .map(Reviewer::getName)
                .orElse(StringUtils.EMPTY);
    }

    @Named("getIsDraft")
    public boolean getIsDraft(String title) {
        return title.contains("Draft:");
    }
}
