package com.syemon.repositorystatistics.infrastructure;

import com.syemon.repositorystatistics.application.ContributorStatisticsMongoCommandHandler;
import com.syemon.repositorystatistics.application.ContributorStatisticsMongoCommandRepository;
import com.syemon.repositorystatistics.application.ContributorStatisticsMongoQueryHandler;
import com.syemon.repositorystatistics.application.ContributorStatisticsMongoQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public ContributorStatisticsMongoCommandHandler contributorStatisticsMongoCommandHandler(ContributorStatisticsMongoCommandRepository repository) {
        return new ContributorStatisticsMongoCommandHandler(repository);
    }

    @Bean
    public ContributorStatisticsMongoQueryHandler contributorStatisticsMongoQueryHandler(ContributorStatisticsMongoQueryRepository repository) {
        return new ContributorStatisticsMongoQueryHandler(repository);
    }

}
