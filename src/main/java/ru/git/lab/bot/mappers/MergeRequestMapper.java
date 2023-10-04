package ru.git.lab.bot.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.git.lab.bot.api.mr.DetailedMergeStatus;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.Reviewer;
import ru.git.lab.bot.dto.AuthorDto;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.services.api.UserService;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserService.class)
public abstract class MergeRequestMapper implements ToDtoMapper<MergeRequestEvent, MergeRequestDto> {

    @Autowired
    private UserService userService;

    @Override
    @Mapping(source = "objectAttributes.id", target = "mrId")
    @Mapping(source = "objectAttributes.title", target = "title")
    @Mapping(source = "objectAttributes.description", target = "description")
    @Mapping(source = "objectAttributes.sourceBranch", target = "sourceBranch")
    @Mapping(source = "objectAttributes.targetBranch", target = "targetBranch")
    @Mapping(source = "objectAttributes.url", target = "mrUrl")
    @Mapping(source = "objectAttributes.action", target = "action")
    @Mapping(source = "objectAttributes.detailedMergeStatus", target = "detailedMergeStatus", qualifiedByName = "getDetailedMergeStatus")
    @Mapping(source = "objectAttributes.authorId", target = "author", qualifiedByName = "getAuthor")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "reviewers", target = "reviewerName", qualifiedByName = "getReviewerName")
    public abstract MergeRequestDto toDto(MergeRequestEvent event);

    @Named("getDetailedMergeStatus")
    public DetailedMergeStatus getDetailedMergeStatus(String value) {
        return DetailedMergeStatus.getStatus(value);
    }

    @Named("getReviewerName")
    public String getReviewerName(List<Reviewer> reviewers) {
        return reviewers.stream()
                .findFirst()
                .map(Reviewer::getName)
                .orElse(StringUtils.EMPTY);
    }

    @Named("getAuthor")
    public AuthorDto getAuthor(long authorId) {
        GitUserEntity user = userService.getByAuthorId(authorId);

        AuthorDto dto = new AuthorDto();
        dto.setId(authorId);
        dto.setName(user.getName());

        return dto;
    }
}
