package com.syemon.repositorystatistics.infrastructure.out.rest;

import lombok.Data;

@Data
public class GithubUserModel {
    private String login;
    private Long id;
    private String nodeId;
    private String avatarUrl;
    private String gravatarId;
    private String url;
    private String htmlUrl;
    private String followersUrl;
    private String followingUrl;
    private String gistsUrl;
    private String starredUrl;
    private String subscriptionsUrl;
    private String organizationsUrl;
    private String reposUrl;
    private String eventsUrl;
    private String receivedEventsUrl;
    private String type;
    private String userViewType;
    private Boolean siteAdmin;
}
