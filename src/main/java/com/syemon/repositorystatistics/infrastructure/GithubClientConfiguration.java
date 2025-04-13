package com.syemon.repositorystatistics.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GithubClientConfiguration {

    @Bean
    public GithubClient githubClient(
            @Value("${github.base-url}") String baseUrl,
            GithubProjectStatisticsMapper githubProjectStatisticsMapper
    ) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return new GithubClient(webClient, githubProjectStatisticsMapper);
    }
}
