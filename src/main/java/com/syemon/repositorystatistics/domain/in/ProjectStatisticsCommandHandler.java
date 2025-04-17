package com.syemon.repositorystatistics.domain.in;

import com.syemon.repositorystatistics.domain.ProjectStatistics;
import reactor.core.publisher.Mono;

public interface ProjectStatisticsCommandHandler {

    Mono<ProjectStatistics> save(ProjectStatistics entity);
}
