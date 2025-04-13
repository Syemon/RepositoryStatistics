package com.syemon.repositorystatistics.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectStatistics {

    @Id
    private ObjectId id;

    private String name;
    private String description;
    private Instant queryTime;
    private Long size;
    private Long watchers;
    private Long forks;
    private Integer openIssues;
    private Long subscribers;

    private List<ContributorStatistics> contributors = new ArrayList<>();
}
