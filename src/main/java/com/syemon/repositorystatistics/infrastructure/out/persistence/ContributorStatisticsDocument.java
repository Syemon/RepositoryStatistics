package com.syemon.repositorystatistics.infrastructure.out.persistence;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
public class ContributorStatisticsDocument {

    @Id
    private ObjectId id;

    private Instant queryTime;
    private String name;
    private Integer contributions;
}
