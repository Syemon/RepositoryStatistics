package com.syemon.repositorystatistics.infrastructure;

import com.syemon.repositorystatistics.domain.ContributorStatistics;
import com.syemon.repositorystatistics.domain.ProjectStatistics;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GithubProjectStatisticsMapper {

    @Mapping(target = "id", expression = "java(generateObjectId())")
    @Mapping(target = "queryTime", expression = "java(instantNow())")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "watchers", target = "watchers")
    @Mapping(source = "forks", target = "forks")
    @Mapping(source = "openIssues", target = "openIssues")
    @Mapping(source = "subscribersCount", target = "subscribers")
    @Mapping(target = "contributors", expression = "java(emptyList())")
    ProjectStatistics toProjectStatistics(GithubRepositoryModel model);

    @Named("generateObjectId")
    default ObjectId generateObjectId() {
        return new ObjectId();
    }

    @Named("instantNow")
    default Instant instantNow() {
        return Instant.now();
    }

    @Named("emptyList")
    default List<ContributorStatistics> emptyList() {
        return Collections.emptyList();
    }
}