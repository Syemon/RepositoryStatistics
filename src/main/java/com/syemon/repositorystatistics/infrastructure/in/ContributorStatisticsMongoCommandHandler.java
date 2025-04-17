package com.syemon.repositorystatistics.infrastructure.in;

import com.syemon.repositorystatistics.application.in.ContributorStatisticsMongoCommandRepository;
import com.syemon.repositorystatistics.domain.ContributorStatistics;
import com.syemon.repositorystatistics.domain.in.ContributorStatisticsCommandHandler;
import com.syemon.repositorystatistics.infrastructure.ContributorStatisticsDocument;
import com.syemon.repositorystatistics.infrastructure.ContributorStatisticsMapper;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ContributorStatisticsMongoCommandHandler implements ContributorStatisticsCommandHandler {

    private final ContributorStatisticsMongoCommandRepository repository;
    private final ContributorStatisticsMapper mapper;

    Mono<ContributorStatisticsDocument> save(ContributorStatisticsDocument entity) {
        return repository.save(entity);
    }

    @Override
    public Mono<ContributorStatistics> save(ContributorStatistics contributorStatistics) {
        ContributorStatisticsDocument document = mapper.toContributorStatisticsDocument(contributorStatistics);
        Mono<ContributorStatisticsDocument> persistedDocument = repository.save(document);

        return persistedDocument.map(mapper::toContributorStatistics);
    }
}
