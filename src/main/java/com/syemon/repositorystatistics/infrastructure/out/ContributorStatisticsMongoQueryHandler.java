package com.syemon.repositorystatistics.infrastructure.out;

import com.syemon.repositorystatistics.application.out.ContributorStatisticsMongoQueryRepository;
import com.syemon.repositorystatistics.domain.ContributorStatistics;
import com.syemon.repositorystatistics.domain.out.ContributorStatisticsQueryHandler;
import com.syemon.repositorystatistics.infrastructure.ContributorStatisticsMapper;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ContributorStatisticsMongoQueryHandler implements ContributorStatisticsQueryHandler {

    private final ContributorStatisticsMongoQueryRepository repository;
    private final ContributorStatisticsMapper contributorStatisticsMapper;

    @Override
    public Mono<ContributorStatistics> findById(ObjectId id) {
        return repository.findById(id)
                .map(contributorStatisticsMapper::toContributorStatistics);
    }
}
