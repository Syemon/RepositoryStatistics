package com.syemon.repositorystatistics.infrastructure;

import com.syemon.repositorystatistics.domain.ContributorStatistics;
import com.syemon.repositorystatistics.domain.ContributorStatisticsCommand;
import com.syemon.repositorystatistics.domain.ProjectStatistics;
import com.syemon.repositorystatistics.domain.ProjectStatisticsCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class GithubClient {

    private static final String GET_REPOSITORY_ENDPOINT = "/repos/{owner}/{repo}";
    private static final String GET_REPOSITORY_CONTRIBUTORS_ENDPOINT = "/repos/{owner}/{repo}/contributors";

    private final WebClient webClient;
    private final GithubProjectStatisticsMapper githubProjectStatisticsMapper;
    private final ContributorStatisticsMapper contributorStatisticsMapper;

    public Mono<ProjectStatistics> getProjectStatistics(ProjectStatisticsCommand request) {
        return webClient.get()
                .uri(GET_REPOSITORY_ENDPOINT, request.ownerName(), request.repositoryName())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GithubRepositoryModel.class)
                .map(githubProjectStatisticsMapper::toProjectStatistics);
    }

    public Flux<ContributorStatistics> getContributorStatistics(ContributorStatisticsCommand request) {
        return webClient.get()
                .uri(GET_REPOSITORY_CONTRIBUTORS_ENDPOINT, request.ownerName(), request.repositoryName())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(GithubContributorModel.class)
                .map(contributorStatisticsMapper::toContributorStatistics);
    }
}
