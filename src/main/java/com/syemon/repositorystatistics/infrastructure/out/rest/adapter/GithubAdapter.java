package com.syemon.repositorystatistics.infrastructure.out.rest.adapter;

import com.syemon.repositorystatistics.domain.ContributorStatistics;
import com.syemon.repositorystatistics.domain.ContributorStatisticsCommand;
import com.syemon.repositorystatistics.domain.ProjectStatistics;
import com.syemon.repositorystatistics.domain.ProjectStatisticsCommand;
import com.syemon.repositorystatistics.domain.out.ContributorStatisticsPort;
import com.syemon.repositorystatistics.domain.out.ProjectStatisticsPort;
import com.syemon.repositorystatistics.infrastructure.ContributorStatisticsMapper;
import com.syemon.repositorystatistics.infrastructure.ProjectStatisticsMapper;
import com.syemon.repositorystatistics.infrastructure.out.rest.GithubClient;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class GithubAdapter implements ProjectStatisticsPort, ContributorStatisticsPort {
    private final GithubClient githubRestClient;
    private final ProjectStatisticsMapper projectStatisticsMapper;
    private final ContributorStatisticsMapper contributorStatisticsMapper;

    @Override
    public Flux<ContributorStatistics> getContributorStatistics(ContributorStatisticsCommand command) {
        return githubRestClient.getContributorStatistics(command)
                .map(contributorStatisticsMapper::toContributorStatistics);
    }

    @Override
    public Mono<ProjectStatistics> getProjectStatistics(ProjectStatisticsCommand command) {
        return githubRestClient.getProjectStatistics(command)
                .map(projectStatisticsMapper::toProjectStatistics);
    }
}
