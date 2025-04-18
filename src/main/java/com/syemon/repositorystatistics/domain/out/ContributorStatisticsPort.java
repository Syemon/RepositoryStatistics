package com.syemon.repositorystatistics.domain.out;

import com.syemon.repositorystatistics.domain.ContributorStatistics;
import com.syemon.repositorystatistics.domain.ContributorStatisticsCommand;
import reactor.core.publisher.Flux;

public interface ContributorStatisticsPort {
    Flux<ContributorStatistics> getContributorStatistics(ContributorStatisticsCommand command);
}
