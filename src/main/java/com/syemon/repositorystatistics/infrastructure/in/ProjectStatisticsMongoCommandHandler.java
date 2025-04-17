package com.syemon.repositorystatistics.infrastructure.in;

import com.syemon.repositorystatistics.application.in.ProjectStatisticsMongoCommandRepository;
import com.syemon.repositorystatistics.domain.ProjectStatistics;
import com.syemon.repositorystatistics.domain.in.ProjectStatisticsCommandHandler;
import com.syemon.repositorystatistics.infrastructure.ProjectStatisticsDocument;
import com.syemon.repositorystatistics.infrastructure.ProjectStatisticsMapper;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ProjectStatisticsMongoCommandHandler implements ProjectStatisticsCommandHandler {

    private final ProjectStatisticsMongoCommandRepository repository;
    private final ProjectStatisticsMapper projectStatisticsMapper;

    @Override
    public Mono<ProjectStatistics> save(ProjectStatistics projectStatistics) {
        ProjectStatisticsDocument document = projectStatisticsMapper.toProjectStatisticsDocument(projectStatistics);

        return repository.save(document)
                .map(projectStatisticsMapper::toProjectStatistics);
    }

}
