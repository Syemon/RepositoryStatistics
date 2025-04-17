package com.syemon.repositorystatistics.domain.out;

import com.syemon.repositorystatistics.domain.ProjectStatistics;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

public interface ProjectStatisticsQueryHandler {

    Mono<ProjectStatistics> findById(ObjectId id);
}
