package com.example.UniWork.Repositories;

import com.example.UniWork.Entity.ProfileEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProfileRepository extends MongoRepository<ProfileEntity, ObjectId> {
    List<ProfileEntity> findBySkillsContainingIgnoreCase(String skills);
    List<ProfileEntity> findByLocationContainingIgnoreCase(String location);
    List<ProfileEntity> findBySkillsContainingIgnoreCaseAndLocationContainingIgnoreCase(String skills, String location);

}
