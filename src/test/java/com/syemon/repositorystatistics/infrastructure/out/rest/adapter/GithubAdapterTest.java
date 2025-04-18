package com.syemon.repositorystatistics.infrastructure.out.rest.adapter;

import com.syemon.repositorystatistics.domain.ContributorStatistics;
import com.syemon.repositorystatistics.domain.ContributorStatisticsCommand;
import com.syemon.repositorystatistics.domain.ProjectStatistics;
import com.syemon.repositorystatistics.domain.ProjectStatisticsCommand;
import com.syemon.repositorystatistics.infrastructure.out.rest.GithubClient;
import com.syemon.repositorystatistics.infrastructure.out.rest.GithubContributorModel;
import com.syemon.repositorystatistics.infrastructure.out.rest.GithubRepositoryModel;
import com.syemon.repositorystatistics.infrastructure.out.rest.GithubUserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.within;
import static org.mockito.Mockito.when;

@SpringBootTest
class GithubAdapterTest {

    public static final String OWNER_NAME = "OwnerName";
    public static final String REPOSITORY_NAME = "RepositoryName";
    @Autowired
    private GithubAdapter githubAdapter;

    @MockitoBean
    private GithubClient githubClient;

    private GithubContributorModel contributor1;
    private GithubContributorModel contributor2;
    GithubRepositoryModel repository;

    @BeforeEach
    public void setUp() {
        contributor1 = new GithubContributorModel();
        contributor1.setLogin("scordio");
        contributor1.setId(26772046L);
        contributor1.setNodeId("MDQ6VXNlcjI2NzcyMDQ2");
        contributor1.setAvatarUrl("https://avatars.githubusercontent.com/u/26772046?v=4");
        contributor1.setGravatarId("");
        contributor1.setUrl("https://api.github.com/users/scordio");
        contributor1.setHtmlUrl("https://github.com/scordio");
        contributor1.setFollowersUrl("https://api.github.com/users/scordio/followers");
        contributor1.setFollowingUrl("https://api.github.com/users/scordio/following{/other_user}");
        contributor1.setGistsUrl("https://api.github.com/users/scordio/gists{/gist_id}");
        contributor1.setStarredUrl("https://api.github.com/users/scordio/starred{/owner}{/repo}");
        contributor1.setSubscriptionsUrl("https://api.github.com/users/scordio/subscriptions");
        contributor1.setOrganizationsUrl("https://api.github.com/users/scordio/orgs");
        contributor1.setReposUrl("https://api.github.com/users/scordio/repos");
        contributor1.setEventsUrl("https://api.github.com/users/scordio/events{/privacy}");
        contributor1.setReceivedEventsUrl("https://api.github.com/users/scordio/received_events");
        contributor1.setType("User");
        contributor1.setUserViewType("public");
        contributor1.setSiteAdmin(false);
        contributor1.setContributions(639);

        contributor2 = new GithubContributorModel();
        contributor2.setLogin("PascalSchumacher");
        contributor2.setId(2707245L);
        contributor2.setNodeId("MDQ6VXNlcjI3MDcyNDU=");
        contributor2.setAvatarUrl("https://avatars.githubusercontent.com/u/2707245?v=4");
        contributor2.setGravatarId("");
        contributor2.setUrl("https://api.github.com/users/PascalSchumacher");
        contributor2.setHtmlUrl("https://github.com/PascalSchumacher");
        contributor2.setFollowersUrl("https://api.github.com/users/PascalSchumacher/followers");
        contributor2.setFollowingUrl("https://api.github.com/users/PascalSchumacher/following{/other_user}");
        contributor2.setGistsUrl("https://api.github.com/users/PascalSchumacher/gists{/gist_id}");
        contributor2.setStarredUrl("https://api.github.com/users/PascalSchumacher/starred{/owner}{/repo}");
        contributor2.setSubscriptionsUrl("https://api.github.com/users/PascalSchumacher/subscriptions");
        contributor2.setOrganizationsUrl("https://api.github.com/users/PascalSchumacher/orgs");
        contributor2.setReposUrl("https://api.github.com/users/PascalSchumacher/repos");
        contributor2.setEventsUrl("https://api.github.com/users/PascalSchumacher/events{/privacy}");
        contributor2.setReceivedEventsUrl("https://api.github.com/users/PascalSchumacher/received_events");
        contributor2.setType("User");
        contributor2.setUserViewType("public");
        contributor2.setSiteAdmin(false);
        contributor2.setContributions(464);

        repository = new GithubRepositoryModel();
        repository.setId(8779606L);
        repository.setNodeId("MDEwOlJlcG9zaXRvcnk4Nzc5NjA2");
        repository.setName("assertj");
        repository.setFullName("assertj/assertj");
        repository.setIsPrivate(false);

        GithubUserModel owner = new GithubUserModel();
        owner.setLogin("assertj");
        owner.setId(18898355L);
        owner.setNodeId("MDEyOk9yZ2FuaXphdGlvbjE4ODk4MzU1");
        owner.setAvatarUrl("https://avatars.githubusercontent.com/u/18898355?v=4");
        owner.setGravatarId("");
        owner.setUrl("https://api.github.com/users/assertj");
        owner.setHtmlUrl("https://github.com/assertj");
        owner.setType("Organization");
        owner.setUserViewType("public");
        owner.setSiteAdmin(false);
        repository.setOwner(owner);

        repository.setHtmlUrl("https://github.com/assertj/assertj");
        repository.setDescription("AssertJ is a library providing easy to use rich typed assertions ");
        repository.setFork(false);
        repository.setUrl("https://api.github.com/repos/assertj/assertj");
        repository.setForksUrl("https://api.github.com/repos/assertj/assertj/forks");
        repository.setKeysUrl("https://api.github.com/repos/assertj/assertj/keys{/key_id}");
        repository.setCollaboratorsUrl("https://api.github.com/repos/assertj/assertj/collaborators{/collaborator}");
        repository.setTeamsUrl("https://api.github.com/repos/assertj/assertj/teams");
        repository.setHooksUrl("https://api.github.com/repos/assertj/assertj/hooks");
        repository.setIssueEventsUrl("https://api.github.com/repos/assertj/assertj/issues/events{/number}");
        repository.setEventsUrl("https://api.github.com/repos/assertj/assertj/events");
        repository.setAssigneesUrl("https://api.github.com/repos/assertj/assertj/assignees{/user}");
        repository.setBranchesUrl("https://api.github.com/repos/assertj/assertj/branches{/branch}");
        repository.setTagsUrl("https://api.github.com/repos/assertj/assertj/tags");
        repository.setBlobsUrl("https://api.github.com/repos/assertj/assertj/git/blobs{/sha}");
        repository.setGitTagsUrl("https://api.github.com/repos/assertj/assertj/git/tags{/sha}");
        repository.setGitRefsUrl("https://api.github.com/repos/assertj/assertj/git/refs{/sha}");
        repository.setTreesUrl("https://api.github.com/repos/assertj/assertj/git/trees{/sha}");
        repository.setStatusesUrl("https://api.github.com/repos/assertj/assertj/statuses/{sha}");
        repository.setLanguagesUrl("https://api.github.com/repos/assertj/assertj/languages");
        repository.setStargazersUrl("https://api.github.com/repos/assertj/assertj/stargazers");
        repository.setContributorsUrl("https://api.github.com/repos/assertj/assertj/contributors");
        repository.setSubscribersUrl("https://api.github.com/repos/assertj/assertj/subscribers");
        repository.setSubscriptionUrl("https://api.github.com/repos/assertj/assertj/subscription");
        repository.setCommitsUrl("https://api.github.com/repos/assertj/assertj/commits{/sha}");
        repository.setGitCommitsUrl("https://api.github.com/repos/assertj/assertj/git/commits{/sha}");
        repository.setCommentsUrl("https://api.github.com/repos/assertj/assertj/comments{/number}");
        repository.setIssueCommentUrl("https://api.github.com/repos/assertj/assertj/issues/comments{/number}");
        repository.setContentsUrl("https://api.github.com/repos/assertj/assertj/contents/{+path}");
        repository.setCompareUrl("https://api.github.com/repos/assertj/assertj/compare/{base}...{head}");
        repository.setMergesUrl("https://api.github.com/repos/assertj/assertj/merges");
        repository.setArchiveUrl("https://api.github.com/repos/assertj/assertj/{archive_format}{/ref}");
        repository.setDownloadsUrl("https://api.github.com/repos/assertj/assertj/downloads");
        repository.setIssuesUrl("https://api.github.com/repos/assertj/assertj/issues{/number}");
        repository.setPullsUrl("https://api.github.com/repos/assertj/assertj/pulls{/number}");
        repository.setMilestonesUrl("https://api.github.com/repos/assertj/assertj/milestones{/number}");
        repository.setNotificationsUrl("https://api.github.com/repos/assertj/assertj/notifications{?since,all,participating}");
        repository.setLabelsUrl("https://api.github.com/repos/assertj/assertj/labels{/name}");
        repository.setReleasesUrl("https://api.github.com/repos/assertj/assertj/releases{/id}");
        repository.setDeploymentsUrl("https://api.github.com/repos/assertj/assertj/deployments");
        repository.setCreatedAt(ZonedDateTime.parse("2013-03-14T16:18:49Z"));
        repository.setUpdatedAt(ZonedDateTime.parse("2025-03-20T13:35:46Z"));
        repository.setPushedAt(ZonedDateTime.parse("2025-03-19T22:34:21Z"));
        repository.setGitUrl("git://github.com/assertj/assertj.git");
        repository.setSshUrl("git@github.com:assertj/assertj.git");
        repository.setCloneUrl("https://github.com/assertj/assertj.git");
        repository.setSvnUrl("https://github.com/assertj/assertj");
        repository.setHomepage("https://assertj.github.io/doc/");
        repository.setSize(50748L);
        repository.setStargazersCount(2678);
        repository.setWatchersCount(2678L);
        repository.setLanguage("Java");
        repository.setHasIssues(true);
        repository.setHasProjects(true);
        repository.setHasDownloads(true);
        repository.setHasWiki(true);
        repository.setHasPages(true);
        repository.setHasDiscussions(true);
        repository.setForksCount(723L);
        repository.setArchived(false);
        repository.setDisabled(false);
        repository.setOpenIssuesCount(268);
        repository.setAllowForking(true);
        repository.setIsTemplate(false);
        repository.setWebCommitSignoffRequired(false);
        repository.setTopics(List.of("assertions", "assertj", "hacktoberfest", "java", "testing", "typed-assertions"));
        repository.setVisibility("public");
        repository.setForks(723);
        repository.setOpenIssues(268);
        repository.setWatchers(2678);
        repository.setDefaultBranch("main");
        repository.setCustomProperties(Map.of());
        repository.setNetworkCount(723);
        repository.setSubscribersCount(71L);
    }

    @Test
    void getContributorStatistics() {
        //given
        ContributorStatisticsCommand command = new ContributorStatisticsCommand(OWNER_NAME, REPOSITORY_NAME);

        when(githubClient.getContributorStatistics(command)).thenReturn(
                Flux.just(contributor1, contributor2)
        );

        //when
        Flux<ContributorStatistics> actual = githubAdapter.getContributorStatistics(command);

        //then
        StepVerifier
                .create(actual)
                .consumeNextWith(statistics -> {
                    assertThat(statistics.getId()).isNotNull();
                    assertThat(statistics.getName()).isEqualTo("scordio");
                    assertThat(statistics.getContributions()).isEqualTo(639);
                    assertThat(statistics.getQueryTime()).isCloseTo(Instant.now(), within(5, ChronoUnit.SECONDS));
                })
                .consumeNextWith(statistics -> {
                    assertThat(statistics.getId()).isNotNull();
                    assertThat(statistics.getName()).isEqualTo("PascalSchumacher");
                    assertThat(statistics.getContributions()).isEqualTo(464);
                    assertThat(statistics.getQueryTime()).isCloseTo(Instant.now(), within(5, ChronoUnit.SECONDS));
                })
                .verifyComplete();
    }

    @Test
    void getProjectStatistics() {
        //given
        ProjectStatisticsCommand command = new ProjectStatisticsCommand(OWNER_NAME, REPOSITORY_NAME);

        when(githubClient.getProjectStatistics(command)).thenReturn(
                Mono.just(repository)
        );

        //when
        Mono<ProjectStatistics> actual = githubAdapter.getProjectStatistics(command);

        //then
        StepVerifier
                .create(actual)
                .assertNext(statistics -> {
                    assertThat(statistics.getId()).isNotNull();
                    assertThat(statistics.getName()).isEqualTo("assertj");
                    assertThat(statistics.getDescription()).isEqualTo("AssertJ is a library providing easy to use rich typed assertions ");
                    assertThat(statistics.getSize()).isEqualTo(50748L);
                    assertThat(statistics.getWatchers()).isEqualTo(2678);
                    assertThat(statistics.getForks()).isEqualTo(723);
                    assertThat(statistics.getOpenIssues()).isEqualTo(268);
                    assertThat(statistics.getSubscribers()).isEqualTo(71L);
                    assertThat(statistics.getQueryTime()).isCloseTo(Instant.now(), within(5, ChronoUnit.SECONDS));
                })
                .verifyComplete();
    }
}