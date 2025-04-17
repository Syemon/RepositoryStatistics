package com.syemon.repositorystatistics.infrastructure;

import com.syemon.repositorystatistics.infrastructure.in.ContributorStatisticsMongoCommandHandler;
import com.syemon.repositorystatistics.infrastructure.out.persistence.ContributorStatisticsMongoCommandRepository;
import com.syemon.repositorystatistics.infrastructure.out.persistence.ContributorStatisticsMongoQueryHandler;
import com.syemon.repositorystatistics.infrastructure.out.persistence.ContributorStatisticsMongoQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public ContributorStatisticsMongoCommandHandler contributorStatisticsMongoCommandHandler(
            ContributorStatisticsMongoCommandRepository repository,
            ContributorStatisticsMapper contributorStatisticsMapper
    ) {
        return new ContributorStatisticsMongoCommandHandler(repository, contributorStatisticsMapper);
    }

    @Bean
    public ContributorStatisticsMongoQueryHandler contributorStatisticsMongoQueryHandler(
            ContributorStatisticsMongoQueryRepository repository,
            ContributorStatisticsMapper contributorStatisticsMapper
    ) {
        return new ContributorStatisticsMongoQueryHandler(repository, contributorStatisticsMapper);
    }

}
