package com.example.UniWork.Repositories;

import com.example.UniWork.Entity.RequirementEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequirementRepository extends MongoRepository<RequirementEntity, ObjectId> {

}
