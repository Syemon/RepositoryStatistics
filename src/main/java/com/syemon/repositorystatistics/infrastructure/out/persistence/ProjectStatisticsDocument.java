package com.syemon.repositorystatistics.infrastructure.out.persistence;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;

@Data
public class ProjectStatisticsDocument {

    @Id
    private ObjectId id;

    private String name;
    private String description;
    private String ownerName;
    private Instant queryTime;
    private Long size;
    private Long watchers;
    private Long forks;
    private Long openIssues;
    private Long subscribers;

    private List<ContributorStatisticsDocument> contributors;
}
