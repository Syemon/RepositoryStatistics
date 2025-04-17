package com.syemon.repositorystatistics.application;

import com.syemon.repositorystatistics.infrastructure.ProjectStatisticsDocument;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ProjectStatisticsMongoCommandHandler {

    private final ProjectStatisticsMongoCommandRepository repository;

    Mono<ProjectStatisticsDocument> save(ProjectStatisticsDocument entity) {
        return repository.save(entity);
    }
}
