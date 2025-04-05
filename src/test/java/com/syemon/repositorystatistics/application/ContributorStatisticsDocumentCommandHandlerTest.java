package com.syemon.repositorystatistics.application;

import com.syemon.repositorystatistics.AbstractBaseMongoDbIntegrationTest;
import com.syemon.repositorystatistics.infrastructure.ContributorStatisticsDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ContributorStatisticsDocumentCommandHandlerTest extends AbstractBaseMongoDbIntegrationTest {

    private static final String CONTRIBUTOR_NAME = "Lorem Ipsum";
    private static final Instant QUERY_TIME = Instant.now();
    private static final int CONTRIBUTIONS = 53123;

    @Autowired
    private ContributorStatisticsMongoCommandHandler sut;

    @Test
    void save() {
        //given
        ContributorStatisticsDocument contributorStatistics = new ContributorStatisticsDocument();
        contributorStatistics.setName(CONTRIBUTOR_NAME);
        contributorStatistics.setQueryTime(QUERY_TIME);
        contributorStatistics.setContributions(CONTRIBUTIONS);

        //when
        Mono<ContributorStatisticsDocument> savedEntity = sut.save(contributorStatistics);

        //then
        StepVerifier
                .create(savedEntity)
                .assertNext(entity -> {
                    assertThat(entity.getId()).isNotNull();
                    assertThat(entity.getName()).isEqualTo(CONTRIBUTOR_NAME);
                    assertThat(entity.getQueryTime()).isEqualTo(QUERY_TIME);
                })
                .verifyComplete();
    }
}