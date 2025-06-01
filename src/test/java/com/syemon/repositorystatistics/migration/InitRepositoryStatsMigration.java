package com.syemon.repositorystatistics.migration;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.Date;

@Slf4j
@ChangeUnit(id = "init-repository-stats", order = "001")
public class InitRepositoryStatsMigration {

    @Execution
    public void createAdminAndInitData(MongoTemplate mongoTemplate, MongoDatabase database) {
        try {
            Document createUserCommand = new Document("createUser", "admin")
                    .append("pwd", "password")
                    .append("roles", Arrays.asList(
                            new Document("role", "root").append("db", "admin")
                    ));
            database.runCommand(createUserCommand);
            log.info("Admin user created successfully!");
        } catch (Exception e) {
           log.error("Note: {}", e.getMessage());
        }

        if (!mongoTemplate.collectionExists("projectStatisticsDocument")) {
            mongoTemplate.createCollection("projectStatisticsDocument");
            log.info("Collection 'projectStatisticsDocument' created successfully!");
        }

        Document project1 = new Document("name", "sample-project")
                .append("description", "A sample repository for testing")
                .append("ownerName", "test-user")
                .append("queryTime", new Date())
                .append("size", 1024)
                .append("watchers", 10)
                .append("forks", 5)
                .append("openIssues", 3)
                .append("subscribers", 7)
                .append("contributors", Arrays.asList(
                        new Document("queryTime", new Date())
                                .append("name", "contributor1")
                                .append("contributions", 15),
                        new Document("queryTime", new Date())
                                .append("name", "contributor2")
                                .append("contributions", 42)
                ));

        Document project2 = new Document("name", "spring-boot-demo")
                .append("description", "Spring Boot demonstration repository")
                .append("ownerName", "admin")
                .append("queryTime", new Date())
                .append("size", 2048)
                .append("watchers", 25)
                .append("forks", 12)
                .append("openIssues", 8)
                .append("subscribers", 15)
                .append("contributors", Arrays.asList(
                        new Document("queryTime", new Date())
                                .append("name", "contributor2")
                                .append("contributions", 37),
                        new Document("queryTime", new Date())
                                .append("name", "contributor3")
                                .append("contributions", 27),
                        new Document("queryTime", new Date())
                                .append("name", "contributor4")
                                .append("contributions", 14)
                ));

        try {
            mongoTemplate.getCollection("projectStatisticsDocument").insertMany(Arrays.asList(project1, project2));
            System.out.println("Sample data inserted successfully!");
        } catch (Exception e) {
            log.error("Note: {}", e.getMessage());
        }

        mongoTemplate.getCollection("projectStatisticsDocument")
                .createIndex(Indexes.ascending("name"), new IndexOptions().unique(true));
        mongoTemplate.getCollection("projectStatisticsDocument")
                .createIndex(Indexes.ascending("ownerName"));
        mongoTemplate.getCollection("projectStatisticsDocument")
                .createIndex(Indexes.ascending("contributors.name"));
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection("projectStatisticsDocument");
    }
}