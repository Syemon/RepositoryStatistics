package com.syemon.repositorystatistics.domain.in;

import com.syemon.repositorystatistics.domain.ContributorStatistics;
import reactor.core.publisher.Mono;

public interface ContributorStatisticsCommandHandler {

    Mono<ContributorStatistics> save(ContributorStatistics entity);
}
