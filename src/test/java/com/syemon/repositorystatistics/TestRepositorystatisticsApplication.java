package com.syemon.repositorystatistics;

import org.springframework.boot.SpringApplication;

public class TestRepositorystatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.from(RepositoryStatisticsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
