package com.example.UniWork.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Document(collection = "locations")
public class LocationDBEntity {
    @Id
    ObjectId id;

    String location;
}
