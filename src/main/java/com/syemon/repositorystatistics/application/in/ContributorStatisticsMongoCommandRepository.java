package com.syemon.repositorystatistics.application.in;

import com.syemon.repositorystatistics.infrastructure.ContributorStatisticsDocument;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorStatisticsMongoCommandRepository extends ReactiveCrudRepository<ContributorStatisticsDocument, ObjectId> {


}
