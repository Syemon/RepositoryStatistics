package com.syemon.repositorystatistics.infrastructure;

import com.syemon.repositorystatistics.infrastructure.out.rest.GithubClient;
import com.syemon.repositorystatistics.infrastructure.out.rest.adapter.GithubAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GithubClientConfiguration {

    @Bean
    public GithubClient githubClient(
            @Value("${github.base-url}") String baseUrl
    ) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return new GithubClient(webClient);
    }

    @Bean
    public GithubAdapter githubAdapter(
            GithubClient githubClient,
            ProjectStatisticsMapper projectStatisticsMapper,
            ContributorStatisticsMapper contributorStatisticsMapper
    ) {
        return new GithubAdapter(
                githubClient,
                projectStatisticsMapper,
                contributorStatisticsMapper
        );
    }
}
