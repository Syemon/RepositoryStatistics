package com.syemon.repositorystatistics.infrastructure.out.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Data
public class GithubRepositoryModel {
    private Long id;
    @JsonProperty("node_id")
    private String nodeId;
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("private")
    private Boolean isPrivate;
    private GithubUserModel owner;
    @JsonProperty("html_url")
    private String htmlUrl;
    private String description;
    private Boolean fork;
    private String url;
    @JsonProperty("forks_url")
    private String forksUrl;
    @JsonProperty("keys_url")
    private String keysUrl;
    @JsonProperty("collaborators_url")
    private String collaboratorsUrl;
    @JsonProperty("teams_url")
    private String teamsUrl;
    @JsonProperty("hooks_url")
    private String hooksUrl;
    @JsonProperty("issue_events_url")
    private String issueEventsUrl;
    private String eventsUrl;
    private String assigneesUrl;
    private String branchesUrl;
    private String tagsUrl;
    private String blobsUrl;
    private String gitTagsUrl;
    private String gitRefsUrl;
    private String treesUrl;
    private String statusesUrl;
    private String languagesUrl;
    private String stargazersUrl;
    private String contributorsUrl;
    private String subscribersUrl;
    private String subscriptionUrl;
    private String commitsUrl;
    private String gitCommitsUrl;
    private String commentsUrl;
    private String issueCommentUrl;
    private String contentsUrl;
    private String compareUrl;
    private String mergesUrl;
    private String archiveUrl;
    private String downloadsUrl;
    private String issuesUrl;
    private String pullsUrl;
    private String milestonesUrl;
    private String notificationsUrl;
    private String labelsUrl;
    private String releasesUrl;
    private String deploymentsUrl;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime pushedAt;
    private String gitUrl;
    private String sshUrl;
    private String cloneUrl;
    private String svnUrl;
    private String homepage;
    private Long size;
    private Integer stargazersCount;
    private Long watchersCount;
    private String language;
    private Boolean hasIssues;
    private Boolean hasProjects;
    private Boolean hasDownloads;
    private Boolean hasWiki;
    private Boolean hasPages;
    private Boolean hasDiscussions;
    @JsonProperty("forks_count")
    private Long forksCount;
    private String mirrorUrl;
    private Boolean archived;
    private Boolean disabled;
    @JsonProperty("open_issues_count")
    private Integer openIssuesCount;
    private Boolean allowForking;
    private Boolean isTemplate;
    private Boolean webCommitSignoffRequired;
    private List<String> topics;
    private String visibility;
    private Integer forks;
    @JsonProperty("open_issues")
    private Integer openIssues;
    private Integer watchers;
    private String defaultBranch;
    private String tempCloneToken;
    private Map<String, Object> customProperties;
    private GithubUserModel organization;
    private Integer networkCount;
    @JsonProperty("subscribers_count")
    private Long subscribersCount;
}
