package com.syemon.repositorystatistics.infrastructure;

import com.syemon.repositorystatistics.infrastructure.out.rest.GithubClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GithubClientConfiguration {

    @Bean
    public GithubClient githubClient(
            @Value("${github.base-url}") String baseUrl,
            ProjectStatisticsMapper projectStatisticsMapper,
            ContributorStatisticsMapper contributorStatisticsMapper
    ) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return new GithubClient(
                webClient,
                projectStatisticsMapper,
                contributorStatisticsMapper
        );
    }
}
