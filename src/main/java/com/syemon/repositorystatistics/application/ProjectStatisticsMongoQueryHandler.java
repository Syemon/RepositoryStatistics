package com.syemon.repositorystatistics.application;

import com.syemon.repositorystatistics.infrastructure.ProjectStatisticsDocument;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ProjectStatisticsMongoQueryHandler {

    private final ProjectStatisticsMongoQueryRepository repository;

    Mono<ProjectStatisticsDocument> findById(ObjectId id) {
        return repository.findById(id);
    }
}
