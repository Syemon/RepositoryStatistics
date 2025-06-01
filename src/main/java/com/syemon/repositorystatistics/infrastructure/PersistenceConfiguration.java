package com.syemon.repositorystatistics.infrastructure;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.syemon.repositorystatistics.infrastructure.in.ContributorStatisticsMongoCommandHandler;
import com.syemon.repositorystatistics.infrastructure.out.persistence.ContributorStatisticsMongoCommandRepository;
import com.syemon.repositorystatistics.infrastructure.out.persistence.ContributorStatisticsMongoQueryHandler;
import com.syemon.repositorystatistics.infrastructure.out.persistence.ContributorStatisticsMongoQueryRepository;
import io.mongock.runner.springboot.EnableMongock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableMongock
public class PersistenceConfiguration {

    @Bean
    public MongoTemplate mongoTemplate(
            @Value("${spring.data.mongodb.uri}") String mongoUri,
            @Value("${spring.data.mongodb.database}") String databaseName
    ) {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUri))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return new MongoTemplate(mongoClient, databaseName);
    }

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
