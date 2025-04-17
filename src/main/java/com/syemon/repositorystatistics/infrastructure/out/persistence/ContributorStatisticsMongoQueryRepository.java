package com.syemon.repositorystatistics.infrastructure.out.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorStatisticsMongoQueryRepository extends ReactiveCrudRepository<ContributorStatisticsDocument, ObjectId> {


}
