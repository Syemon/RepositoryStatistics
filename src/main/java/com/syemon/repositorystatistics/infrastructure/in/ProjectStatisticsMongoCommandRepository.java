package com.syemon.repositorystatistics.infrastructure.in;

import com.syemon.repositorystatistics.infrastructure.out.persistence.ProjectStatisticsDocument;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStatisticsMongoCommandRepository extends ReactiveCrudRepository<ProjectStatisticsDocument, ObjectId> {


}
