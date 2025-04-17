package com.syemon.repositorystatistics.infrastructure.out.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorStatisticsMongoCommandRepository extends ReactiveCrudRepository<ContributorStatisticsDocument, ObjectId> {


}
