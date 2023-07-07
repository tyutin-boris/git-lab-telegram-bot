package ru.git.lab.bot.api.mr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Project {
        Long id;
        String name;
        String description;
        @JsonProperty("web_url")
        String webUrl;
        @JsonProperty("avatar_url")
        String avatarUrl;
        @JsonProperty("git_ssh_url")
        String gitSshUrl;
        @JsonProperty("git_http_url")
        String gitHttpUrl;
        String namespace;
        @JsonProperty("visibility_level")
        Long visibilityLevel;
        @JsonProperty("path_with_namespace")
        String pathWithNamespace;
        @JsonProperty("default_branch")
        String defaultBranch;
        @JsonProperty("ci_config_path")
        String ciConfigPath;
        String homepage;
        String url;
        @JsonProperty("ssh_url")
        String sshUrl;
        @JsonProperty("http_url")
        String httpUrl;
}
