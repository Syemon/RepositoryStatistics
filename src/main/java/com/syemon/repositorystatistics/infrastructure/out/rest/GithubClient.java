package com.syemon.repositorystatistics.infrastructure.out.rest;

import com.syemon.repositorystatistics.domain.ContributorStatisticsCommand;
import com.syemon.repositorystatistics.domain.ProjectStatisticsCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class GithubClient {

    private static final String GET_REPOSITORY_ENDPOINT = "/repos/{owner}/{repo}";
    private static final String GET_REPOSITORY_CONTRIBUTORS_ENDPOINT = "/repos/{owner}/{repo}/contributors";

    private final WebClient webClient;

    public Mono<GithubRepositoryModel> getProjectStatistics(ProjectStatisticsCommand request) {
        return webClient.get()
                .uri(GET_REPOSITORY_ENDPOINT, request.ownerName(), request.repositoryName())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GithubRepositoryModel.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                        log.info("Github repository (owner:{}, repository: {}) not found",
                                request.ownerName(), request.repositoryName());
                        return Mono.empty();
                    }
                    return Mono.error(e);
                });
    }

    public Flux<GithubContributorModel> getContributorStatistics(ContributorStatisticsCommand request) {
        return webClient.get()
                .uri(GET_REPOSITORY_CONTRIBUTORS_ENDPOINT, request.ownerName(), request.repositoryName())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(GithubContributorModel.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                        log.info("Github repository contributors (owner:{}, repository: {}) not found",
                                request.ownerName(), request.repositoryName());
                        return Mono.empty();
                    }
                    return Mono.error(e);
                });
    }
}
