package com.syemon.repositorystatistics.infrastructure;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.syemon.repositorystatistics.domain.ContributorStatisticsCommand;
import com.syemon.repositorystatistics.domain.ProjectStatisticsCommand;
import com.syemon.repositorystatistics.infrastructure.out.rest.GithubClient;
import com.syemon.repositorystatistics.infrastructure.out.rest.GithubContributorModel;
import com.syemon.repositorystatistics.infrastructure.out.rest.GithubRepositoryModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubClientTest {

    public static final String OWNER_NAME = "assertj";
    public static final String REPOSITORY_NAME = "assertj";
    public static final String NOT_FOUND_REPOSITORY_NAME = "not_found";
    public static final String NOT_FOUND_OWNER_NAME = "not_found";
    public static final String SERVER_ERROR_OWNER_NAME = "server_error";
    public static final String SERVER_ERROR_REPOSITORY_NAME = "server_error";

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

        //when
        Mono<GithubRepositoryModel> response = sut.getProjectStatistics(command);

        //then
        StepVerifier
                .create(response)
                .assertNext(actual -> {
                    assertThat(actual.getId()).isNotNull();
                    assertThat(actual.getName()).isEqualTo("assertj");
                    assertThat(actual.getDescription()).isEqualTo("AssertJ is a library providing easy to use rich typed assertions ");
                    assertThat(actual.getSize()).isEqualTo(50748L);
                    assertThat(actual.getWatchers()).isEqualTo(2678);
                    assertThat(actual.getForks()).isEqualTo(723);
                    assertThat(actual.getOpenIssues()).isEqualTo(268);
                    assertThat(actual.getSubscribersCount()).isEqualTo(71L);
                })
                .verifyComplete();
    }

    @Test
    void getProjectDetails_shouldReturnEmpty_whenClientReturns404() {
        //given
        ProjectStatisticsCommand command = new ProjectStatisticsCommand(
                NOT_FOUND_OWNER_NAME, NOT_FOUND_REPOSITORY_NAME
        );

        //when
        Mono<GithubRepositoryModel> response = sut.getProjectStatistics(command);

        //then
        StepVerifier
                .create(response)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getProjectDetails_shouldReturnError_whenClientReturnsUnexpectedStatus() {
        //given
        ProjectStatisticsCommand command = new ProjectStatisticsCommand(
                SERVER_ERROR_OWNER_NAME, SERVER_ERROR_REPOSITORY_NAME
        );

        //when
        Mono<GithubRepositoryModel> response = sut.getProjectStatistics(command);

        //then
        StepVerifier
                .create(response)
                .expectError()
                .verify();
    }

    @Test
    void getContributorDetails() {
        //given
        ContributorStatisticsCommand command = new ContributorStatisticsCommand(
                OWNER_NAME, REPOSITORY_NAME
        );

        //when
        Flux<GithubContributorModel> response = sut.getContributorStatistics(command);

        //then
        StepVerifier
                .create(response)
                .consumeNextWith(actual -> {
                    assertThat(actual.getId()).isNotNull();
                    assertThat(actual.getLogin()).isEqualTo("scordio");
                    assertThat(actual.getContributions()).isEqualTo(639);
                })
                .consumeNextWith(actual -> {
                    assertThat(actual.getId()).isNotNull();
                    assertThat(actual.getLogin()).isEqualTo("PascalSchumacher");
                    assertThat(actual.getContributions()).isEqualTo(464);
                })
                .verifyComplete();
    }

    @Test
    void getContributorDetails_shouldReturnEmpty_whenClientReturns404() {
        //given
        ContributorStatisticsCommand command = new ContributorStatisticsCommand(
                NOT_FOUND_OWNER_NAME, NOT_FOUND_REPOSITORY_NAME
        );

        //when
        Flux<GithubContributorModel> response = sut.getContributorStatistics(command);

        //then
        StepVerifier
                .create(response)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getContributorDetails_shouldReturnError_whenClientReturnsUnexpectedStatus() {
        //given
        ContributorStatisticsCommand command = new ContributorStatisticsCommand(
                SERVER_ERROR_OWNER_NAME, SERVER_ERROR_REPOSITORY_NAME
        );

        //when
        Flux<GithubContributorModel> response = sut.getContributorStatistics(command);

        //then
        StepVerifier
                .create(response)
                .expectError()
                .verify();
    }
}