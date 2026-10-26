package com.example.UniWork.Repositories;

import com.example.UniWork.Entity.LocationDBEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<LocationDBEntity, ObjectId> {
    LocationDBEntity findByLocation(String location);
}
