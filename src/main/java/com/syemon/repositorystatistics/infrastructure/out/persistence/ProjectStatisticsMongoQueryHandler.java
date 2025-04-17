package com.syemon.repositorystatistics.infrastructure.out.persistence;

import com.syemon.repositorystatistics.domain.ProjectStatistics;
import com.syemon.repositorystatistics.domain.out.ProjectStatisticsQueryHandler;
import com.syemon.repositorystatistics.infrastructure.ProjectStatisticsMapper;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ProjectStatisticsMongoQueryHandler implements ProjectStatisticsQueryHandler {

    private final ProjectStatisticsMongoQueryRepository repository;
    private final ProjectStatisticsMapper projectStatisticsMapper;

    @Override
    public Mono<ProjectStatistics> findById(ObjectId id) {
        return repository.findById(id)
                .map(projectStatisticsMapper::toProjectStatistics);
    }
}
