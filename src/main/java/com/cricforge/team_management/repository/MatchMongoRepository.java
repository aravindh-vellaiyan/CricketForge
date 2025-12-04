package com.cricforge.team_management.repository;

import com.cricforge.team_management.mongo.MatchDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchMongoRepository extends MongoRepository<MatchDocument, String> {
}