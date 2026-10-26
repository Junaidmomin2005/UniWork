package com.example.UniWork.Repositories;

import com.example.UniWork.Entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.ObjectInput;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUserName(String userName);
}
