package com.syemon.repositorystatistics.application;

import com.syemon.repositorystatistics.domain.ProjectStatisticsCommand;
import com.syemon.repositorystatistics.domain.ProjectStatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ProjectStatisticsScheduler {

    private ProjectStatisticsService projectStatisticsService;

    public void fetchAndStoreProjectStatistics() {
        ProjectStatisticsCommand command = new ProjectStatisticsCommand(
                "assertj",
                "assertj"
        );
        projectStatisticsService.saveProjectStatistics(command)
                .doOnSuccess(saved -> log.info("Saved project statistics for: {}", command.repositoryName()))
                .doOnError(e -> log.error("Failed to save project statistics", e));
    }
}
