package com.syemon.repositorystatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRepositorystatisticsApplication extends AbstractBaseMongoDbIntegrationTest {

    public static void main(String[] args) {
        SpringApplication.from(RepositoryStatisticsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
