package com.syemon.repositorystatistics.domain.out;

import com.syemon.repositorystatistics.domain.ProjectStatistics;
import com.syemon.repositorystatistics.domain.ProjectStatisticsCommand;
import reactor.core.publisher.Mono;

public interface ProjectStatisticsPort {
    Mono<ProjectStatistics> getProjectStatistics(ProjectStatisticsCommand command);
}
