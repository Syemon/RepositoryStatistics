package com.syemon.repositorystatistics.domain.out;

import com.syemon.repositorystatistics.domain.ContributorStatistics;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

public interface ContributorStatisticsQueryHandler {

    Mono<ContributorStatistics> findById(ObjectId id);
}
