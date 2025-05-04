package com.syemon.repositorystatistics.domain;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectStatistics {

    private ObjectId id;

    private String name;
    private String ownerName;
    private String description;
    private Instant queryTime;
    private Long size;
    private Long watchers;
    private Long forks;
    private Integer openIssues;
    private Long subscribers;

    private List<ContributorStatistics> contributors = new ArrayList<>();
}
