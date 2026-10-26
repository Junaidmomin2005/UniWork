package com.example.UniWork.Repositories;

import com.example.UniWork.Entity.SkillsDBEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SkillsRepository extends MongoRepository<SkillsDBEntity, ObjectId> {
    SkillsDBEntity findBySkills(String skills);
}
