package com.syemon.repositorystatistics.infrastructure;

import com.syemon.repositorystatistics.domain.ContributorStatistics;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface GithubContributorStatisticsMapper {

    @Mapping(target = "id", expression = "java(generateObjectId())")
    @Mapping(target = "queryTime", expression = "java(instantNow())")
    @Mapping(source = "login", target = "name")
    @Mapping(source = "contributions", target = "contributions")
    ContributorStatistics toContributorStatistics(GithubContributorModel githubContributorModel);

    @Named("generateObjectId")
    default ObjectId generateObjectId() {
        return new ObjectId();
    }

    @Named("instantNow")
    default Instant instantNow() {
        return Instant.now();
    }
}