package ru.git.lab.bot.api.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PipeProject {

    private Long id;

    private String name;

    private String description;

    @JsonProperty("web_url")
    private String webUrl;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("git_ssh_url")
    private String gitSshUrl;

    @JsonProperty("git_http_url")
    private String gitHttpUrl;

    private String namespace;

    @JsonProperty("visibility_level")
    private Integer visibilityLevel;

    @JsonProperty("path_with_namespace")
    private String pathWithNamespace;

    @JsonProperty("default_branch")
    private String defaultBranch;

    @JsonProperty("ci_config_path")
    private String ciConfigPath;
}
