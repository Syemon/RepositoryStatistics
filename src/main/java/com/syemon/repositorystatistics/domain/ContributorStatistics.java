package com.syemon.repositorystatistics.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
public class ContributorStatistics {

    @Id
    private ObjectId id;

    private Instant queryTime;
    private String name;
    private Integer contributions;
}
