package com.syemon.repositorystatistics.application;

import com.syemon.repositorystatistics.infrastructure.ContributorStatisticsDocument;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ContributorStatisticsMongoCommandHandler {

    private final ContributorStatisticsMongoCommandRepository repository;

    Mono<ContributorStatisticsDocument> save(ContributorStatisticsDocument entity) {
        return repository.save(entity);
    }
}
