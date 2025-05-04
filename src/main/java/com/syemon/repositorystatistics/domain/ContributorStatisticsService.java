package com.syemon.repositorystatistics.domain;

import com.syemon.repositorystatistics.domain.in.ProjectStatisticsCommandHandler;
import com.syemon.repositorystatistics.domain.out.ContributorStatisticsPort;
import com.syemon.repositorystatistics.domain.out.ProjectStatisticsPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class ContributorStatisticsService {

    private final ProjectStatisticsPort projectStatisticsPort;
    private final ContributorStatisticsPort contributorStatisticsPort;
    private final ProjectStatisticsCommandHandler projectStatisticsCommandHandler;

    public Mono<ProjectStatistics> saveProjectStatistics(ProjectStatisticsCommand projectStatisticsCommand) {
        return projectStatisticsPort.getProjectStatistics(projectStatisticsCommand)
                .switchIfEmpty(Mono.error(new ProjectNotFoundException("Project not found: " + projectStatisticsCommand.repositoryName())))
                .flatMap(projectStatistics -> fetchAndSetContributors(projectStatistics)
                        .flatMap(projectStatisticsCommandHandler::save)
                        .doOnSuccess(saved -> log.info("Saved project statistics for: {}", projectStatisticsCommand.repositoryName()))
                        .doOnError(e -> log.error("Failed to save project statistics", e))
                );
    }

    private Mono<ProjectStatistics> fetchAndSetContributors(ProjectStatistics projectStatistics) {
        ContributorStatisticsCommand contributorCommand = new ContributorStatisticsCommand(
                projectStatistics.getOwnerName(), projectStatistics.getName());
        return contributorStatisticsPort.getContributorStatistics(contributorCommand)
                .collectList()
                .map(contributors -> {
                    projectStatistics.setContributors(contributors);
                    return projectStatistics;
                });
    }}
