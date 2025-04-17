package com.syemon.repositorystatistics.domain;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Instant;

@Data
public class ContributorStatistics {

    private ObjectId id;

    private Instant queryTime;
    private String name;
    private Integer contributions;
}
