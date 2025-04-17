package com.syemon.repositorystatistics.infrastructure;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.syemon.repositorystatistics.domain.ContributorStatistics;
import com.syemon.repositorystatistics.domain.ContributorStatisticsCommand;
import com.syemon.repositorystatistics.domain.ProjectStatistics;
import com.syemon.repositorystatistics.domain.ProjectStatisticsCommand;
import com.syemon.repositorystatistics.infrastructure.out.rest.GithubClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubClientTest {

    public static final String REPOS_HTTP_200_RESPONSE = """
            {
              "id": 8779606,
              "node_id": "MDEwOlJlcG9zaXRvcnk4Nzc5NjA2",
              "name": "assertj",
              "full_name": "assertj/assertj",
              "private": false,
              "owner": {
                "login": "assertj",
                "id": 18898355,
                "node_id": "MDEyOk9yZ2FuaXphdGlvbjE4ODk4MzU1",
                "avatar_url": "https://avatars.githubusercontent.com/u/18898355?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/assertj",
                "html_url": "https://github.com/assertj",
                "followers_url": "https://api.github.com/users/assertj/followers",
                "following_url": "https://api.github.com/users/assertj/following{/other_user}",
                "gists_url": "https://api.github.com/users/assertj/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/assertj/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/assertj/subscriptions",
                "organizations_url": "https://api.github.com/users/assertj/orgs",
                "repos_url": "https://api.github.com/users/assertj/repos",
                "events_url": "https://api.github.com/users/assertj/events{/privacy}",
                "received_events_url": "https://api.github.com/users/assertj/received_events",
                "type": "Organization",
                "user_view_type": "public",
                "site_admin": false
              },
              "html_url": "https://github.com/assertj/assertj",
              "description": "AssertJ is a library providing easy to use rich typed assertions ",
              "fork": false,
              "url": "https://api.github.com/repos/assertj/assertj",
              "forks_url": "https://api.github.com/repos/assertj/assertj/forks",
              "keys_url": "https://api.github.com/repos/assertj/assertj/keys{/key_id}",
              "collaborators_url": "https://api.github.com/repos/assertj/assertj/collaborators{/collaborator}",
              "teams_url": "https://api.github.com/repos/assertj/assertj/teams",
              "hooks_url": "https://api.github.com/repos/assertj/assertj/hooks",
              "issue_events_url": "https://api.github.com/repos/assertj/assertj/issues/events{/number}",
              "events_url": "https://api.github.com/repos/assertj/assertj/events",
              "assignees_url": "https://api.github.com/repos/assertj/assertj/assignees{/user}",
              "branches_url": "https://api.github.com/repos/assertj/assertj/branches{/branch}",
              "tags_url": "https://api.github.com/repos/assertj/assertj/tags",
              "blobs_url": "https://api.github.com/repos/assertj/assertj/git/blobs{/sha}",
              "git_tags_url": "https://api.github.com/repos/assertj/assertj/git/tags{/sha}",
              "git_refs_url": "https://api.github.com/repos/assertj/assertj/git/refs{/sha}",
              "trees_url": "https://api.github.com/repos/assertj/assertj/git/trees{/sha}",
              "statuses_url": "https://api.github.com/repos/assertj/assertj/statuses/{sha}",
              "languages_url": "https://api.github.com/repos/assertj/assertj/languages",
              "stargazers_url": "https://api.github.com/repos/assertj/assertj/stargazers",
              "contributors_url": "https://api.github.com/repos/assertj/assertj/contributors",
              "subscribers_url": "https://api.github.com/repos/assertj/assertj/subscribers",
              "subscription_url": "https://api.github.com/repos/assertj/assertj/subscription",
              "commits_url": "https://api.github.com/repos/assertj/assertj/commits{/sha}",
              "git_commits_url": "https://api.github.com/repos/assertj/assertj/git/commits{/sha}",
              "comments_url": "https://api.github.com/repos/assertj/assertj/comments{/number}",
              "issue_comment_url": "https://api.github.com/repos/assertj/assertj/issues/comments{/number}",
              "contents_url": "https://api.github.com/repos/assertj/assertj/contents/{+path}",
              "compare_url": "https://api.github.com/repos/assertj/assertj/compare/{base}...{head}",
              "merges_url": "https://api.github.com/repos/assertj/assertj/merges",
              "archive_url": "https://api.github.com/repos/assertj/assertj/{archive_format}{/ref}",
              "downloads_url": "https://api.github.com/repos/assertj/assertj/downloads",
              "issues_url": "https://api.github.com/repos/assertj/assertj/issues{/number}",
              "pulls_url": "https://api.github.com/repos/assertj/assertj/pulls{/number}",
              "milestones_url": "https://api.github.com/repos/assertj/assertj/milestones{/number}",
              "notifications_url": "https://api.github.com/repos/assertj/assertj/notifications{?since,all,participating}",
              "labels_url": "https://api.github.com/repos/assertj/assertj/labels{/name}",
              "releases_url": "https://api.github.com/repos/assertj/assertj/releases{/id}",
              "deployments_url": "https://api.github.com/repos/assertj/assertj/deployments",
              "created_at": "2013-03-14T16:18:49Z",
              "updated_at": "2025-03-20T13:35:46Z",
              "pushed_at": "2025-03-19T22:34:21Z",
              "git_url": "git://github.com/assertj/assertj.git",
              "ssh_url": "git@github.com:assertj/assertj.git",
              "clone_url": "https://github.com/assertj/assertj.git",
              "svn_url": "https://github.com/assertj/assertj",
              "homepage": "https://assertj.github.io/doc/",
              "size": 50748,
              "stargazers_count": 2678,
              "watchers_count": 2678,
              "language": "Java",
              "has_issues": true,
              "has_projects": true,
              "has_downloads": true,
              "has_wiki": true,
              "has_pages": true,
              "has_discussions": true,
              "forks_count": 723,
              "mirror_url": null,
              "archived": false,
              "disabled": false,
              "open_issues_count": 268,
              "license": {
                "key": "apache-2.0",
                "name": "Apache License 2.0",
                "spdx_id": "Apache-2.0",
                "url": "https://api.github.com/licenses/apache-2.0",
                "node_id": "MDc6TGljZW5zZTI="
              },
              "allow_forking": true,
              "is_template": false,
              "web_commit_signoff_required": false,
              "topics": [
                "assertions",
                "assertj",
                "hacktoberfest",
                "java",
                "testing",
                "typed-assertions"
              ],
              "visibility": "public",
              "forks": 723,
              "open_issues": 268,
              "watchers": 2678,
              "default_branch": "main",
              "temp_clone_token": null,
              "custom_properties": {
            
              },
              "organization": {
                "login": "assertj",
                "id": 18898355,
                "node_id": "MDEyOk9yZ2FuaXphdGlvbjE4ODk4MzU1",
                "avatar_url": "https://avatars.githubusercontent.com/u/18898355?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/assertj",
                "html_url": "https://github.com/assertj",
                "followers_url": "https://api.github.com/users/assertj/followers",
                "following_url": "https://api.github.com/users/assertj/following{/other_user}",
                "gists_url": "https://api.github.com/users/assertj/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/assertj/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/assertj/subscriptions",
                "organizations_url": "https://api.github.com/users/assertj/orgs",
                "repos_url": "https://api.github.com/users/assertj/repos",
                "events_url": "https://api.github.com/users/assertj/events{/privacy}",
                "received_events_url": "https://api.github.com/users/assertj/received_events",
                "type": "Organization",
                "user_view_type": "public",
                "site_admin": false
              },
              "network_count": 723,
              "subscribers_count": 71
            }
            """;


    public static final String REPOS_CONTRIBUTORS_HTTP_200_RESPONSE = """
              [
              {
                "login": "scordio",
                "id": 26772046,
                "node_id": "MDQ6VXNlcjI2NzcyMDQ2",
                "avatar_url": "https://avatars.githubusercontent.com/u/26772046?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/scordio",
                "html_url": "https://github.com/scordio",
                "followers_url": "https://api.github.com/users/scordio/followers",
                "following_url": "https://api.github.com/users/scordio/following{/other_user}",
                "gists_url": "https://api.github.com/users/scordio/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/scordio/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/scordio/subscriptions",
                "organizations_url": "https://api.github.com/users/scordio/orgs",
                "repos_url": "https://api.github.com/users/scordio/repos",
                "events_url": "https://api.github.com/users/scordio/events{/privacy}",
                "received_events_url": "https://api.github.com/users/scordio/received_events",
                "type": "User",
                "user_view_type": "public",
                "site_admin": false,
                "contributions": 639
              },
              {
                "login": "PascalSchumacher",
                "id": 2707245,
                "node_id": "MDQ6VXNlcjI3MDcyNDU=",
                "avatar_url": "https://avatars.githubusercontent.com/u/2707245?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/PascalSchumacher",
                "html_url": "https://github.com/PascalSchumacher",
                "followers_url": "https://api.github.com/users/PascalSchumacher/followers",
                "following_url": "https://api.github.com/users/PascalSchumacher/following{/other_user}",
                "gists_url": "https://api.github.com/users/PascalSchumacher/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/PascalSchumacher/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/PascalSchumacher/subscriptions",
                "organizations_url": "https://api.github.com/users/PascalSchumacher/orgs",
                "repos_url": "https://api.github.com/users/PascalSchumacher/repos",
                "events_url": "https://api.github.com/users/PascalSchumacher/events{/privacy}",
                "received_events_url": "https://api.github.com/users/PascalSchumacher/received_events",
                "type": "User",
                "user_view_type": "public",
                "site_admin": false,
                "contributions": 464
              }
              ]
    """;


    public static final String OWNER_NAME = "assertj";
    public static final String REPOSITORY_NAME = "assertj";

    @Autowired
    private GithubClient sut;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("github.base-url", wireMockServer::baseUrl);
    }

    @Test
    void getProjectDetails() {
        //given
        ProjectStatisticsCommand command = new ProjectStatisticsCommand(
                OWNER_NAME, REPOSITORY_NAME
        );

        wireMockServer.stubFor(WireMock.get(urlEqualTo("/repos/assertj/assertj"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(REPOS_HTTP_200_RESPONSE)));

        //when
        Mono<ProjectStatistics> response = sut.getProjectStatistics(command);

        //then
        StepVerifier
                .create(response)
                .assertNext(actual -> {
                    assertThat(actual.getId()).isNotNull();
                    assertThat(actual.getName()).isEqualTo("assertj");
                    assertThat(actual.getDescription()).isEqualTo("AssertJ is a library providing easy to use rich typed assertions ");
                    assertThat(actual.getQueryTime()).isCloseTo(Instant.now(), within(5, ChronoUnit.SECONDS));
                    assertThat(actual.getSize()).isEqualTo(50748L);
                    assertThat(actual.getWatchers()).isEqualTo(2678L);
                    assertThat(actual.getForks()).isEqualTo(723L);
                    assertThat(actual.getOpenIssues()).isEqualTo(268);
                    assertThat(actual.getSubscribers()).isEqualTo(71L);
                })
                .verifyComplete();
    }

    @Test
    void getContributorDetails() {
        //given
        ContributorStatisticsCommand command = new ContributorStatisticsCommand(
                OWNER_NAME, REPOSITORY_NAME
        );

        wireMockServer.stubFor(WireMock.get(urlEqualTo("/repos/assertj/assertj/contributors"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(REPOS_CONTRIBUTORS_HTTP_200_RESPONSE)));

        //when
        Flux<ContributorStatistics> response = sut.getContributorStatistics(command);

        //then
        StepVerifier
                .create(response)
                .consumeNextWith(actual -> {
                    assertThat(actual.getId()).isNotNull();
                    assertThat(actual.getName()).isEqualTo("scordio");
                    assertThat(actual.getQueryTime()).isCloseTo(Instant.now(), within(5, ChronoUnit.SECONDS));
                    assertThat(actual.getContributions()).isEqualTo(639);
                })
                .consumeNextWith(actual -> {
                    assertThat(actual.getId()).isNotNull();
                    assertThat(actual.getName()).isEqualTo("PascalSchumacher");
                    assertThat(actual.getQueryTime()).isCloseTo(Instant.now(), within(5, ChronoUnit.SECONDS));
                    assertThat(actual.getContributions()).isEqualTo(464);
                })
                .verifyComplete();
    }
}