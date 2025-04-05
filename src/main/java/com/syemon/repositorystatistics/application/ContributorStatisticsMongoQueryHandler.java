package com.syemon.repositorystatistics.application;

import com.syemon.repositorystatistics.infrastructure.ContributorStatisticsDocument;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ContributorStatisticsMongoQueryHandler {

    private final ContributorStatisticsMongoQueryRepository repository;

    Mono<ContributorStatisticsDocument> findById(ObjectId id) {
        return repository.findById(id);
    }
}
