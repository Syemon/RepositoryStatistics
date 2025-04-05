package com.syemon.repositorystatistics.infrastructure;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document
@Data
public class ProjectStatisticsDocument {

    @Id
    private ObjectId id;

    private String name;
    private String description;
    private Instant queryTime;
    private Long size;
    private Long watchers;
    private Long forks;
    private Long openIssues;
    private Long subscribers;

    @DBRef
    private List<ContributorStatisticsDocument> contributors;
}
